
### 活动专题设计


由于公司专题开发是每个活动开发一个新的，有大量重复功能，模块，甚至同一个活动重复开发。

活动专题设计目的是减少重复专题开发，做到一个专题模板可以重复使用

设计之初是使用挂件方式可以对不同模板不同专题，后经过几次优化和实际专题应用过程，逐步完善各个功能和核心节点衔接。

该系统设计总体难度不高，但以前虽然设计过好几个系统，大小都有，实际并没有一个完整的架构概念，基本属于前辈经验，本次设计属于沉心构思后设计的系统，属于整体构思设计，完整，完善，对系统设计的理解上升了一个点


### 设计细节

1.功能


设计之初涉及6大模块

专题管理，专题插件管理，专题模板管理，抽奖模块，签到模块，日期数据模块


数据流程：

底层存在基类用于操作数据缓存和获取，当获取数据的业务逻辑需要使用到缓存取和存以及删除时，都可以直接调用此类或继承或use，可以直接使用该类的统一缓存操作方法

这说起来是简单，但是如果没有经过这次实战，很可能会忽略这个设计而在每个数据块内部单独做

这样在下一个统一数据出口的设计里面，对数据获取和更新就会有混乱的情况发生

1.getSpecialData




