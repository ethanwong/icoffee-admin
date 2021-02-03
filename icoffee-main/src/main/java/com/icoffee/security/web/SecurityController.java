package com.icoffee.security.web;

import cn.hutool.core.date.DateUtil;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.utils.SecurityUtils;
import com.icoffee.security.config.authentication.JwtProvider;
import com.icoffee.security.dto.LoginResultDto;
import com.icoffee.security.dto.RouteDto;
import com.icoffee.security.dto.UserInfoDto;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.domain.User;
import com.icoffee.system.dto.LoginUserDto;
import com.icoffee.system.service.MenuService;
import com.icoffee.system.service.UserService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Name SecurityController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Api(tags = {"权限管理-认证API"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/security")
public class SecurityController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtProvider jwtTokenProvider;

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    /**
     * 过期时间间隔
     */
    @Value("${jwt.token.expires}")
    private int EXPIRES;

    /**
     * 是否检查验证码
     */
    @Value("${security.login.captcha}")
    private boolean NEEDCAPTCHA;

    /**
     * 用户登录
     *
     * @param request
     * @param loginUserDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "登录", notes = "提交用户名、密码、验证码进行登录")
    @PostMapping("login")
    public ResultDto login(HttpServletRequest request, @Validated @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {


        if (NEEDCAPTCHA && !CaptchaUtil.ver(loginUserDto.getCode(), request)) {
            CaptchaUtil.clear(request);
            return ResultDto.returnFail("验证码错误!");
        }
        CaptchaUtil.clear(request);

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            //生成TOKEN
            List<RouteDto> permissionList = new ArrayList<>();
            String token = jwtTokenProvider.createToken(loginUserDto.getUsername(), permissionList);

            LoginResultDto loginResultDto = new LoginResultDto();
            loginResultDto.setUsername(loginUserDto.getUsername());
            loginResultDto.setExpireAt(DateUtil.offsetSecond(new Date(), EXPIRES / 1000));
            loginResultDto.setRole("");
            loginResultDto.setPermissionList(permissionList);
            loginResultDto.setToken(token);

            return ResultDto.returnSuccessMessageData("登录成功！", loginResultDto);
        } catch (Exception e) {
            log.error("login error", e);
            return ResultDto.returnFail("账号或者密码错误!");
        }

    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "退出登录", notes = "")
    @PostMapping("logout")
    public ResultDto logout(HttpServletRequest request) {
        //todo 如果有设置缓存，需要将缓存中的token移除
        return ResultDto.returnSuccess();
    }

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "获取验证码", notes = "")
    @GetMapping("/captcha")
    public ResponseEntity<Object> captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int width = 130;
        int height = 48;
        Captcha captcha = new ArithmeticCaptcha(width, height);
        //两位数运算
        captcha.setLen(2);

        String captchaValue = captcha.text();

        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        request.getSession().setAttribute("captcha", captchaValue);
        Map<String, Object> imgResult = new HashMap<String, Object>(1) {{
            put("img", captcha.toBase64());
        }};
        return ResponseEntity.ok(imgResult);
    }

    /**
     * 校验验证码
     *
     * @param request
     * @param response
     * @param captcha
     * @return
     */
    @ApiOperation(value = "校验验证码", notes = "")
    @GetMapping("/captcha/check/{captcha}")
    public boolean check(HttpServletRequest request, HttpServletResponse response, @PathVariable String captcha) {
        String original = (String) request.getSession().getAttribute("captcha");
        log.info("input captcha={} , original captcha={}", captcha, original);

        return CaptchaUtil.ver(captcha, request);
    }

    @ApiOperation(value = "获取用户信息", notes = "")
    @GetMapping("/user")
    public ResultDto getUserInfo() {
        UserInfoDto userInfoDto = new UserInfoDto();
        String currentUsername = SecurityUtils.getCurrentUsername();
        User user = userService.getByUsername(currentUsername);

        userInfoDto.setUsername(currentUsername);
        userInfoDto.getRoles().add("admin");
        userInfoDto.setAvatar("");
//        userInfoDto.setRoutes(genRoutes());
        userInfoDto.setRoutes(genUserRoutes());
        return ResultDto.returnSuccessData(userInfoDto);
    }

    private void setChildrenRoute(Menu child, List<RouteDto> children) {
        Map<String, Object> childMeta = new HashMap<>();
        childMeta.put("title", child.getTitle());
        childMeta.put("icon", child.getIcon());
        RouteDto childRoute = new RouteDto(child.getModuleName(), child.getRoutePath(), child.getComponentPath(), null, childMeta, null, null);
        children.add(childRoute);

        if (child.getChildren() != null && !child.getChildren().isEmpty()) {
            List<RouteDto> subChildren = new ArrayList<>();
            for (Menu sub : child.getChildren()) {
                setChildrenRoute(sub, subChildren);
            }
        }

    }

    private List<RouteDto> genUserRoutes() {
        List<RouteDto> routes = new ArrayList<>();
        List<Menu> list = menuService.getRootMenuList();
        for (Menu menu : list) {

            Map<String, Object> meta = new HashMap<>();
            meta.put("title", menu.getTitle());
            meta.put("icon", menu.getIcon());


            List<RouteDto> children = new ArrayList<>();

            for (Menu child : menu.getChildren()) {
                setChildrenRoute(child, children);
            }

            RouteDto routesDto = new RouteDto(menu.getModuleName(), menu.getRoutePath(), menu.getComponentPath(), null, meta, children, null);
            routes.add(routesDto);
        }

        return routes;
    }

    private List<RouteDto> genRoutes() {
        List<RouteDto> routes = new ArrayList<>();

        Map<String, Object> meta = new HashMap<>();
        meta.put("title", "系统管理");
        meta.put("icon", "lock");

        List<String> roles = new ArrayList<>();
        roles.add("admin");
        meta.put("roles", roles);


        List<RouteDto> children = new ArrayList<>();

        //用户管理
        Map<String, Object> userMeta = new HashMap<>();
        userMeta.put("title", "用户管理");
        userMeta.put("icon", "user");
//        userMeta.put("affix", false);
        RouteDto user = new RouteDto("user", "user", "system/user", null, userMeta, null, null);
        children.add(user);
        //角色管理
        Map<String, Object> roleMeta = new HashMap<>();
        roleMeta.put("title", "角色管理");
        roleMeta.put("icon", "peoples");
//        roleMeta.put("affix", false);
        RouteDto role = new RouteDto("role", "role", "system/role", null, roleMeta, null, null);
        children.add(role);

        //菜单管理
        Map<String, Object> menuMeta = new HashMap<>();
        menuMeta.put("title", "菜单管理");
        menuMeta.put("icon", "tree-table");
//        menuMeta.put("affix", false);
        RouteDto menu = new RouteDto("menu", "menu", "system/menu", null, menuMeta, null, null);
        children.add(menu);


        //系统管理
        RouteDto routesDto = new RouteDto("system", "/system", "Layout", null, meta, children, null);

        routes.add(routesDto);

        return routes;
    }

}
