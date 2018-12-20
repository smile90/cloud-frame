package com.frame.user.shiro;

import com.frame.user.shiro.SysAuthMatcher;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.AntPathMatcher;
import org.apache.shiro.util.PatternMatcher;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

/**
 * 系统权限基于角色匹配
 * @author: duanchangqing90
 * @date: 2018/12/19
 */
@Slf4j
@Service
public class SysAuthByRoleMatcher implements SysAuthMatcher {

    @Setter
    protected PatternMatcher pathMatcher = new AntPathMatcher();


    /**
     * 配置路径权限
     */
    @Override
    public void processPathConfig() {
        // TODO
        log.info("processPathConfig");
    }

    @Override
    public String[] getPathConfig(Subject subject, ServletRequest request) {
        String path = getPathWithinApplication(request);
        String[] roles = null;



        // TODO
        log.info("path:{}, roles:{}", path, roles);
        return new String[] {"test"};
    }

    protected String getPathWithinApplication(ServletRequest request) {
        return WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
    }
}
