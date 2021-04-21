package com.dazhi.console.demo;

import com.dazhi.console.sentinel.SentinelResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("order")
public class OrderController {
    private static final String KEY = "getOrderInfo";

    @RequestMapping("/getOrder")
    @ResponseBody
    @SentinelResource()
    public String queryOrder1(@RequestParam("orderId") String orderId) {
        return orderId;
        /*Entry entry = null;
        // 资源名
        String resourceName = KEY;
        try {
            // entry可以理解成入口登记
            entry = SphU.entry(resourceName);

            // 被保护的逻辑, 这里为订单查询接口
            return orderId;
        } catch (BlockException blockException) {
            // 接口被限流的时候, 会进入到这里
//            log.warn("---getOrder1接口被限流了---, exception: ", blockException);
            return "接口限流, 返回空";
        } finally {

            // SphU.entry(xxx) 需要与 entry.exit() 成对出现,否则会导致调用链记录异常
            if (entry != null) {
                entry.exit();
            }
        }*/
    }

    /**
     * 初始化限流配置
     */
    /*@PostConstruct
    public void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(KEY);
        // QPS控制在2以内
        rule1.setCount(2);
        // QPS限流
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }*/
}
