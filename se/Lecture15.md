### Lecture15

#### Yi Zhao 11612917

#### Lecturer Tan, Shin Hwei



![1559959718949](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559959718949.png)

**Continuous Delivery** (持续交付)

- The essence of my philosophy to software delivery is to build software so that it is
  always in a state where it could be put into production.

- Continuous running a deployment pipeline

- The key is automated testing



Continuous Delivery = Continuous Integration (持续集成) + Automated test suite

Continuous Deployment = CD + Automatic Deployment



**Deployment strategies:**

1. Zero-downtime deployment 

Deploy version 1 of the service and migrate the database to a new version, then deploy version 2 of the service parallel to version 1. If version 2 works fine, bring down to version 1.

1. Blue-green deployment

Maintain two copies of the production environment. **Route all traffic** to the **blue** environment by mapping URLs to it. **Deploy and test any changes** to the application in the **green** environment.

Advantage: user can use application without downtime.

Disadvantage: need to maintain two copies, migration of database may not be backend compatible.



**Regression Testing**

testing that are performed to ensure that changes made does not break existing functionality.



**Flaky Tests ** (不稳定的，在相同配置下可能成功也可能失败的测试)

sources: parallel, dependency, resource leak, network, IO, randomness, floating point operations, unordered collections



Software security

- Confidentiality

- Integrity
- Availability



