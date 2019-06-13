### Lecture6,9,10

#### Yi Zhao 11612917

#### Lecturer Tan, Shin Hwei



**Maven**

- A project management and comprehension tool

**POM**

- Single configuration file that contains the majority of information required to build a project


**Maven Phases**
Common default lifecycle phases:

- validate: validate the project is correct and all necessary information is available

- compile: compile the source code of the project

- test: test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed

- package: take the compiled code and package it in its distributable format, such as a JAR.

- integration-test: process and deploy the package if necessary into an environment where integration tests can be run

- verify: run any checks to verify the package is valid and meets quality criteria

- install: install the package into the local repository, for use as a dependency in other projects locally

- deploy: done in an integration or release environment, copies the final package to the remote repository for sharing with other developers and projects.

  **There are two other Maven lifecycles of note beyond the default list above. They are**

  - clean: cleans up artifacts created by prior builds
  - site: generates site documentation for this project



**Lines of code is valid metric when**

- Same language
- Standard formatting
- Code has been reviewed



**CYCLOMATIC COMPLEXITY**

![1559903807169](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559903807169.png)

- Cyclomatic complexity is the number of independent paths through the procedure

- Gives an upper bound on the number of tests necessary to execute every edge of control graph

![1559904117934](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559904117934.png)

The cyclomatic complexity for the above case is 14.

```java
if(){
    if(){
        
    }else{
        
    }
}else{
    
}
```

The cyclomatic complexity for the above case is 5.



**FUNCTION POINTS**

- Way of measuring “functionality” of system
- Measure of how big a system ought to be
- Used to predict size



- Ca : Afferent(传入) coupling: the number of classes outside this module that
  depend on classes inside this module
- Ce : Efferent coupling(传出): the number of classes inside this module that
  depend on classes outside this module
- **Instability = Ce / (Ca + Ce)**

![1559922049211](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559922049211.png)

For example, as relationship like the picture above,the ca value will be 3, and the ce value will be 1.

The instability will be 
$$
I = \frac{Ce}{Ce + Ca}\\ = 0.75
$$
对于外面的类的依赖越多，包的可靠性越低。

**Abstractness**
$$
A = \frac{T_{abstract}}{T_{abstract} + T_{concrete}}
$$
(number of abstract classes in module / number of classes in module)

![1559924224046](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559924224046.png)

**TECHNICAL OO METRICS**

• Weighted Methods Per Class (WMC): WMC for a class is the sum of the complexities of the methods in the class
• Depth of Inheritance Tree (DIT): Maximum length from a class to the root of the tree
• Number of Children (NOC): Number of immediate subclasses
• Coupling between Object Classes (CBO): Number of other classes to which a class, Class A is coupled to class B if there is a method in A that invokes a method of B
• Response for a Class (RFC): Number of methods in a class or called by a class
• Lack of Cohesion in Methods (LCOM): Number of pairs of methods that do not share instance variables minus number of pairs of methods that share instance variables.



• Forward engineering (从业务逻辑开始编写代码)
   From requirements to design to code
• Reverse engineering (拿到源代码，分析业务逻辑)
   From code to design, maybe to requirements
• Reengineering
   From old code to new code via some design



**Static Analysis**

- Checkstyle (check coding standard)
- PMD (scan source code and look for potential problems)
- FindBugs (inspect Java bytecode for occurrences of bug patterns)





