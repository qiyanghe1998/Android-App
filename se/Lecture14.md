### Lecture14

#### Yi Zhao 11612917

#### Lecturer Tan, Shin Hwei



**Command Design Pattern**

![1559954403561](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559954403561.png)



**Implementation concerns**

- Simplicity
- Safety
- Use standard libraries/toolkits
- Separate UI from application



**Documentation:**

![1559954835930](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559954835930.png)

![1559954853901](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559954853901.png)

javadoc comments begin with /** and end with */, and in javadoc, a * at the beginning of the line is not part of the comment text.

In a doc comment, you must replace

![1559955254677](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559955254677.png)



Tags in doc comments

![1559955379951](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559955379951.png)

> Rules for writing summaries
>
> - The first sentence should summarize the purpose of the element
> - For methods, omit the subject and write in the third-person narrative form
>    Good: Finds the first blank in the string.
>    Not as good: Find the first blank in the string.
>    Bad: This method finds the first blank in the string.
>    Worse: Method findBlank(String s) finds the first blank in the string.
> - Use the word this rather than “the” when referring to instances of the current class (for example, Multiplies this fraction…)
> - Do not add parentheses to a method or constructor name unless you
>   want to specify a particular signature



There are three “standard” flags that you can put into
any comment

- TODO -- describes a feature that should be added
- FIXME -- describes a bug in the method
- XXX -- this needs to be thought about some more



![1559956023369](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559956023369.png)



**DevOps and Continuous Integration**

Development + IT Operations

开发和运行必须紧密结合



**Continuous Integration** (持续集成)

is a software development practice where members of a team integrate their work frequently, usually each person integrates at least daily - leading to multiple integrations per day. Each integration is verified by an automated build (including test) to detect integration errors as quickly as possible. **持续集成是一种软件开发实践，即团队开发成员经常集成他们的工作，通常每个成员每天至少集成一次，也就意味着每天可能会发生多次集成。每次集成都通过自动化的构建（包括编译，发布，自动化测试）来验证，从而尽早地发现集成错误**



**10 Principles of Continuous Integration**

 Maintain a code repository – version control
 Automate the build
 Make your build self-testing
 Everyone commits to mainline every day
 Every commit should build mainline on an integration machine
 Keep the build fast
 Test in a clone of the production environment
 Make it easy for anyone to get the latest executable
 Everyone can see what's happening
 Automate deployment



**Smoke Test**
 A quick set of tests run on the daily build.
 Cover most important functionalities of the software but NOT exhaustive (详尽)

**For example:**

> How do you perform smoke test for a new cup?
>
> Fill it with water. If there is a leaking, then it is a bad cup



**Continuous Integration server**
An external machine that automatically pulls your latest repo code   and fully builds all resources.



Levels of Software Testing

- Acceptance testing
- System testing ()
- Integration testing (Verify software quality by testing two or more dependent software modules as a group.)
- Unit testing (Test individual units of a software)