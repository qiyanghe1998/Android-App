Asset (需要被保护的实体，如数据，软件本身): Something of value which has to be protected. The asses may be the software system itself or data used by that system.

Attack（利用系统脆弱性对系统进行破坏）: An exploitation of a system's vulnerability. Generally, this is from outside the system and is a deliberate attempt to cause some damage.

Control（降低系统脆弱性的保护性方式，如加密）: A protective measure that reduce a system's vulnerability. Encryption is an example of a control that reduces a vulnerability of a weak access control system.

Exposure（系统可能遭受的伤害或损失）: Possible loss or harm to a computing system. This can be loss or damage to data, or can be a loss of time and effort if recovery is necessary after a security breach.

Threat（对于某个特定攻击，系统的脆弱之处） Circumstances that have potential to cause loss or harm. The aspects as a system vulnerability that is subjected to an attack.

 Vulnerability（计算系统中可以被利用造成损失或伤害的脆弱之处）: A weakness in a computer-based system that may be exploited to cause loss or harm.



SCM:

- Change Control
- Version Control
- Building 
- Releasing



Change control: Change request / engineering change order (修改请求 / 工程订单修改)

- new feature
- bug report

Change control authority - decides which changes should be carried out.

Should link code changes to change requests.



Version Control:

- is a software system that keeps track of the changes made to a set of files so that you can recall a specific version. 



Build Mangement

- Use which compiler, which flags, which source files, which libraries should be linked.
- daily build and smoke test



Release

- Release is a software configuration item that the developers give to other people.
- Release should be a baseline.



Tools:

- Version Control: cvs, subversion, svn, git
- change control: bugzilla, mantis, jira
- building: make, ant, mvn
- Releasing: Maven central and Nexus ...



Waterfall model:

**Requirements -> Design -> Implementation -> Integration or Testing -> Maintenance**



eXtreme Programming XP:

- Different from rigid waterfall process
  - Replace it with a collaborative and iterative design process.
- Main ideas
  - Working on code and test more instead of writing much documentation
  - Implement features one by one
  - Release code frequently
  - Work closely with the customer
  - Communicate a lot with team members



XP: Some key practices

- Planning game for requirements

  - customer writes user stories and programmer estimate time to do each story, is the story is big, customer splits the story.

- Test-driven development for design and testing

  - Write tests first, then write code.

- Refactoring for design.

  - Because code is as simple as it can be, adding a new feature tends to make it simple, to recover simplicity, you must refactor the code. To refactor safely, you should have a rigorous set of unit tests.

- Pair programming for development

  - two programmers work side-by-side at one computer, continuously collaborating on the same design, algorithm, code and test.

- Continuous Integration for integration

  - **持续集成是一种软件开发实践，即团队开发成员经常集成他们的工作，通常每个成员每天至少集成一次，也就意味着每天可能会发生多次集成。每次集成都通过自动化的构建（包括编译，发布，自动化测试）来验证，从而尽早地发现集成错误**

  Continuous Delivery = Continuous Integration (持续集成) + Automated test suite

  Continuous Deployment = CD + Automatic Deployment


Terminology

Mistake: programmer makes a **mistake** **->**

**Fault** appears in the program **->**

Fault remains undetected during testing **->**

Program **failure** occurs duiring execution (programs behaves unexpectedly) **->**

**Error**: difference between computed value and theoritically correct value.



Test-Driven Development (TDD)

**Add a test -> Run the tests -> Make a little change -> Run the tests**



Coverage criteria

- Instruction coverage
- Statements Coverage
- Branch Coverage
- Method Coverage
- Class Coverage



Cyclomatic Complexity 

= Number of branches (if, while, for) + 1



Instability = Ce / (Ca + Ce)

Ca: Affeent coupling measure incoming dependencies

Ce: Efferent coupling measure outgoing dependencies



Reverse engineering

From lower level to higher level, for example:

- Given binary, discover source code
- Given code, discover specification & design rationale



Static Analysis:

- Checkstyle
- PMD
- FindBugs



Smoke Test: 

- A quick set of tests on the daily build



**Design -> code -> build -> integrate -> test -> release -> deploy**



Continuous Delivery = Continuous Integration (持续集成) + Automated test suite

Continuous Deployment = CD + Automatic Deployment