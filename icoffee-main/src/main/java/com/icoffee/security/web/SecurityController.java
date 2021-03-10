package com.icoffee.security.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icoffee.common.dto.AuthorityDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.utils.RsaUtils;
import com.icoffee.common.utils.SecurityUtils;
import com.icoffee.security.config.authentication.JwtProvider;
import com.icoffee.security.dto.LoginResultDto;
import com.icoffee.security.dto.RouteDto;
import com.icoffee.security.dto.TokenDto;
import com.icoffee.security.dto.UserInfoDto;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.domain.Role;
import com.icoffee.system.domain.User;
import com.icoffee.system.dto.LoginUserDto;
import com.icoffee.system.service.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

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
     * 密码私钥
     */
    @Value("${security.rsa.private_key}")
    private String PRIMARYKEY;

    @ApiOperation(value = "用户登录", notes = "提交用户名、密码、验证码进行登录")
    @PostMapping("login")
    public ResultDto login(HttpServletRequest request, @Validated @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) throws Exception {
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(PRIMARYKEY, loginUserDto.getPassword());
        loginUserDto.setPassword(password);

        log.info("user login user=" + loginUserDto);

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
            TokenDto accessToken = jwtTokenProvider.createAccessToken(loginUserDto.getUsername());
            TokenDto refreshToken = jwtTokenProvider.createRefreshToken(loginUserDto.getUsername());


            LoginResultDto loginResultDto = new LoginResultDto();
            loginResultDto.setUsername(loginUserDto.getUsername());
            loginResultDto.setAccessToken(accessToken);
            loginResultDto.setRefreshToken(refreshToken);

            return ResultDto.returnSuccessMessageData("登录成功！", loginResultDto);
        } catch (Exception e) {
            log.error("login error", e);
            return ResultDto.returnFail(e.getMessage());
        }

    }

    @ApiOperation(value = "刷新token", notes = "刷新token重新登录")
    @PostMapping("/refreshToken")
    public ResultDto refreshToken(HttpServletRequest request) {

        //获取用户名称
        String username = SecurityUtils.getCurrentUsername();

        //生成TOKEN
        TokenDto accessToken = jwtTokenProvider.createAccessToken(username);
        TokenDto refreshToken = jwtTokenProvider.createRefreshToken(username);


        LoginResultDto loginResultDto = new LoginResultDto();
        loginResultDto.setUsername(username);
        loginResultDto.setAccessToken(accessToken);
        loginResultDto.setRefreshToken(refreshToken);

        return ResultDto.returnSuccessMessageData("重新登录成功！", loginResultDto);
    }

    @ApiOperation(value = "退出登录", notes = "")
    @PostMapping("logout")
    public ResultDto logout(HttpServletRequest request) {
        //todo 如果有设置缓存，需要将缓存中的token移除
        return ResultDto.returnSuccess();
    }

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

        Role role = roleService.getRoleById(user.getRoleId());
        userInfoDto.setId(user.getId());
        userInfoDto.setUsername(currentUsername);
        userInfoDto.setRealname(user.getRealname());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setPhoneNumber(user.getPhoneNumber());
        userInfoDto.setAvatar("");
        userInfoDto.setGender(user.getGender());
        userInfoDto.getRoles().add(role.getName());

        //获取路由信息
        userInfoDto.setRoutes(getUserRoutes(role));
        //获取权限信息
        userInfoDto.setPermissions(getPermissions(user));

        return ResultDto.returnSuccessData(userInfoDto);
    }

    /**
     * 获取授权信息
     *
     * @param user
     * @return
     */
    private List<AuthorityDto> getPermissions(User user) {
        return userService.getUserGrantedAuthorities(user.getRoleId());
    }

    /**
     * 获取用户的路由信息
     *
     * @return
     */
    private List<RouteDto> getUserRoutes(Role role) {
        List<RouteDto> routes = new ArrayList<>();
        //跟级菜单
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        List<Menu> rootMenuList = menuService.getRootMenuList(queryWrapper);

        //获取当前用户所属角色所关联的菜单
        List<String> checkMenuIds = roleMenuService.getMenuIdsByRoleId(role.getId());
        boolean filterMenu = true;

        //超级管理员展示所有菜单路由
        if (role.getName().equals("root")) {
            filterMenu = false;
        }

        //生成路由
        for (Menu rootMenu : rootMenuList) {

            if (filterMenu && !checkMenuIds.contains(rootMenu.getId())) {
                continue;
            }

            Map<String, Object> meta = new HashMap<>();
            meta.put("title", rootMenu.getTitle());
            meta.put("icon", rootMenu.getIcon());

            //子级路由
            List<RouteDto> children = new ArrayList<>();
            for (Menu child : rootMenu.getChildren()) {
                if (filterMenu && !checkMenuIds.contains(child.getId())) {
                    continue;
                }
                setChildrenRoute(child, children, checkMenuIds, filterMenu);
            }
            RouteDto rootRouteDto = new RouteDto(rootMenu.getModuleName(), rootMenu.getRoutePath(), rootMenu.getComponentPath(), meta, children, rootMenu.getHidden());
            routes.add(rootRouteDto);

            //判断跟级路径是否路由重定向,根据vue路由格式要求，对带重定向参数的跟级别目录进行处理
            if (rootMenu.isRedirect()) {
                //重定向的目标路径和组件的相对路径
                String redirect = rootMenu.getRoutePath();
                if (redirect.startsWith("/")) {
                    redirect = redirect.substring(1);
                }
                boolean isInclude = false;
                for (RouteDto child : rootRouteDto.getChildren()) {
                    if (child.getPath().equals(redirect)) {
                        isInclude = true;
                        break;
                    }
                }

                if (!isInclude) {
                    //不包含name参数
                    rootRouteDto.setName(null);
                    //不包含meta参数
                    rootRouteDto.setMeta(null);
                    //path路径为空
                    rootRouteDto.setPath("");
                    rootRouteDto.getChildren().add(new RouteDto(rootMenu.getModuleName(), redirect, redirect, meta, null, rootMenu.getHidden()));
                }
            }
        }

        return routes;
    }

    /**
     * 设置子级路由
     *
     * @param child
     * @param children
     */
    private void setChildrenRoute(Menu child, List<RouteDto> children, List<String> menuIds, boolean filterMenu) {
        Map<String, Object> childMeta = new HashMap<>();
        childMeta.put("title", child.getTitle());
        childMeta.put("icon", child.getIcon());
        List<RouteDto> subChildren = new ArrayList<>();
        if (child.getChildren() != null && !child.getChildren().isEmpty()) {
            for (Menu sub : child.getChildren()) {

                if (filterMenu && !menuIds.contains(sub.getId())) {
                    continue;
                }

                setChildrenRoute(sub, subChildren, menuIds, filterMenu);
            }
        }
        children.add(new RouteDto(child.getModuleName(), child.getRoutePath(), child.getComponentPath(), childMeta, subChildren, child.getHidden()));
    }
}
