package com.github.hgaol.miaomiao.controller;

import com.github.hgaol.miaomiao.domain.MiaoshaUser;
import com.github.hgaol.miaomiao.domain.OrderInfo;
import com.github.hgaol.miaomiao.redis.RedisService;
import com.github.hgaol.miaomiao.result.CodeMsg;
import com.github.hgaol.miaomiao.result.Result;
import com.github.hgaol.miaomiao.service.GoodsService;
import com.github.hgaol.miaomiao.service.MiaoshaUserService;
import com.github.hgaol.miaomiao.service.OrderService;
import com.github.hgaol.miaomiao.vo.GoodsVo;
import com.github.hgaol.miaomiao.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

  @Autowired
  MiaoshaUserService userService;

  @Autowired
  RedisService redisService;

  @Autowired
  OrderService orderService;

  @Autowired
  GoodsService goodsService;

  @RequestMapping("/detail")
  @ResponseBody
  public Result<OrderDetailVo> info(Model model, MiaoshaUser user,
                                    @RequestParam("orderId") long orderId) {
    if (user == null) {
      return Result.error(CodeMsg.SESSION_ERROR);
    }
    OrderInfo order = orderService.getOrderById(orderId);
    if (order == null) {
      return Result.error(CodeMsg.ORDER_NOT_EXIST);
    }
    long goodsId = order.getGoodsId();
    GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    OrderDetailVo vo = new OrderDetailVo();
    vo.setOrder(order);
    vo.setGoods(goods);
    return Result.success(vo);
  }

}
