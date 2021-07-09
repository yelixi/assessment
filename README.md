###  1. 权限控制
root --管理员权限
corrector --批改成绩人员
issuer --发布题目人员
member --普通成员  
权限：
root：注册全体用户并设置不同权限。
corrector：仅可批改自己负责的那部分题目
issuer：负责自定义并发布题目
member：回答题目

### 2. 功能
root：
	- 新增用户(corrector issuer member)
	- 删除用户
	- 修改用户权限
issuer：
	- 修改corrector负责的内容
	- 布置题目(自定义题目及内容：自定义题干，回答形式(单选、多选、判断、简答等))
	- 新增用户(corrector member)
	- 删除用户
corrector：
	- 批改题目(仅可批改自己负责的部分)
	- 修改评分
	- 对member回答发表评论
member：
	- 保存答案
	- 提交答案
成绩汇总
成绩以表单形式导出
