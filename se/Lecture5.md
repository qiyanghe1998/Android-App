### Lecture5

#### Yi Zhao 11612917

#### Lecturer Tan, Shin Hwei



**Write good test**

- Each Test should be independent to each other
  - 在不同测试方法中不要使用同一个实例对象
-  Any given behaviour should be specified in one and only one test.
  - 每一个测试方法只测试一个feature
- Correct method signature should be assertEquals(expected,actual)
  - 使用assertEquals方法时，期望值应当放在第一个参数的位置
- Use correct method to give out the testing result
  - 例如，使用equals方法比较字符串



Code coverage is a measure used to describe the degree to which the source code of a program is executed when a particular test suite runs

- Code Coverage is classified as a White box testing
- White Box testing: Testing where internal structure/ design/ implementation of
  the item being tested is known



**Give reasonable and meaningful name for test methods and the instantiated objects.**