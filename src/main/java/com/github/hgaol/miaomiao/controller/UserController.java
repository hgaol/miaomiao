package com.github.hgaol.miaomiao.controller;

import com.github.hgaol.miaomiao.domain.MiaoshaUser;
import com.github.hgaol.miaomiao.redis.RedisService;
import com.github.hgaol.miaomiao.result.Result;
import com.github.hgaol.miaomiao.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    MiaoshaUserService userService;
	
	@Autowired
    RedisService redisService;
	
    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user) {
        return Result.success(user);
    }
    
}
