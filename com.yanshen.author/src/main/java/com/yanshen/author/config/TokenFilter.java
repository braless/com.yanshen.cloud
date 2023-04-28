package com.yanshen.author.config;

import com.yanshen.author.entity.BaseQueryPO;
import com.yanshen.author.entity.Result;
import java.io.IOException;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.alipay.sofa.common.utils.StringUtil;
import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.yanshen.author.util.SecurityUtil;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;


/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-01 14:29
 **/
@Provider
@Priority(101)
public class TokenFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        String path = ctx.getUriInfo().getPath();
        if(!path.startsWith("/openapi/security")) {
            String lyzhtoken = null;
            List<String> headers = ctx.getHeaders().get("lyzhtoken");
            if(headers!=null) {
                lyzhtoken = headers.get(0);
            }
            if(lyzhtoken == null || StringUtil.isBlank(lyzhtoken)) {
                lyzhtoken = RpcInvokeContext.getContext().getRequestBaggage("lyzhtoken");
            }
            if(lyzhtoken == null || !StringUtil.isNotBlank(lyzhtoken)) {
                Response response = Response.ok(Result.fail(null, "UnAuthorized", null)).status(200).type(MediaType.APPLICATION_JSON).build();
                throw new WebApplicationException(response);
            }else {
                try {
                    BaseQueryPO systeminfo = SecurityUtil.verifyToken(lyzhtoken);
                    SecurityUtil.setActiveSystem(systeminfo);
                } catch (Exception e) {
                    Response response = Response.ok(Result.fail(null, "TokenInvalid", null)).status(200).type(MediaType.APPLICATION_JSON).build();
                    throw new WebApplicationException(response);
                }
            }
        }
    }

}

