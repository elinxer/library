package com.zhiteer.demo;


import com.zhiteer.demo.model.DemoUser2Model;
import com.zhiteer.demo.model.DemoUserModel;
import com.zhiteer.demo.model.mapper.DemoUser2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/demo/model")
public class DemoModelController {

    // 创建线程安全的Map，模拟users信息的存储
    static Map<Long, DemoUserModel> users = Collections.synchronizedMap(new HashMap<Long, DemoUserModel>());

    @Autowired
    private DemoUser2Mapper userMapper;


    /**
     * 处理"/demo/model/"的GET请求，用来获取用户列表
     *
     * @return
     */
    @GetMapping("/list")
    public List<DemoUserModel> getUserList() {
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        return new ArrayList<DemoUserModel>(users.values());
    }

    /**
     * 处理"/demo/model"的POST请求，用来创建User
     *
     * @param user
     * @return
     */
    @PostMapping("/doUser")
    public String postUser(@RequestBody DemoUserModel user) {
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        users.put(user.getId(), user);
        return "success";
    }

    /**
     * 处理"/demo/model/{id}"的GET请求，用来获取url中id值的User信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public DemoUserModel getUser(@PathVariable Long id) {
        // url中的id可通过@PathVariable绑定到函数的参数中
        return users.get(id);
    }

    /**
     * 处理"/demo/model/{id}"的PUT请求，用来更新User信息
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public String putUser(@PathVariable Long id, @RequestBody DemoUserModel user) {
        DemoUserModel u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    /**
     * 处理"/demo/model/{id}"的DELETE请求，用来删除User
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }

    public String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    @RequestMapping("/tSelect")
    public String tSelect() {

        String username = "elinx" + this.getTimeStamp();

        int userId = userMapper.insert(username, 27);
        DemoUser2Model User = userMapper.findByName(username);

        return "UserId: " + userId + ", name: " + User.getName();
    }

}
