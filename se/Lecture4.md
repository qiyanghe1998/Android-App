### Lecture4

#### Yi Zhao 11612917

#### Lecturer Tan, Shin Hwei



**JUnit test fixtures**

```java
public class Original {
	public int a = 0;
	Original(){
		System.out.println("The constructor is called.");
	}
	public int getA() {
		return a;
		
	}
}

public class OriginalTest {
	private Original original;
	
	@Before
	public void runBeforeEachTest() {
		original = new Original();
	}
	
	@After
	public void runAfterEachTest() {
		original = null;
	}
	
    @Test
    public void test1() {
    	original.a = 10;
    	assertEquals(original.getA(), 10);
    }
    
    @Test
    public void test2() {
    	original.a = 12;
    	assertEquals(original.getA(), 12);
    }
}
```

```java
Output:
The constructor is called.
The constructor is called.
```

![1559894810529](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559894810529.png)

- A problem with this “test” is that it actually combines four separate tests in one method

- With automation, small tests allow us to more easily identify failures …

- JUnit provides test drivers
- If a test fails, JUnit gives the location of the failure and any exceptions that were thrown



**State Testing Patterns**

​	**Final State Assertion**

1. Most Common Pattern: Arrange. Act. Assert.
**Guard Assertion**
1. Assert Both Before and After The Action (Precondition Testing)
**Delta Assertion**
1. Verify a Relative Change to the State
**Custom Assertion**
1. Encodes Complex Verification Rules



![1559895670080](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559895670080.png)

**参数单元测试：**

使用场景

对于同一个测试函数，有多个组测试输入



**JUNIT THEORIES**

These Are Unit Tests With Actual Parameters![1559897913596](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559897913596.png)

Data comes from Annotation @Datapoints

![1559897962829](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559897962829.png)

```java
@RunWith(Theories.class)
public class TheoriesTest{
    @DataPoints
	public static String[] names = {"Tony", "Jim"};
	@DataPoints
	public static int[] ageValue1 = {10, 20};
    @Theory
    public void testMethod(String name, int age){
        System.out.println(String.format("%s's age is %s", name, age));
    }
}
```

上面的代码的意思是，将”Tony”、”Jim”、10、20四个参数以类型合法的排列组合传给待没方法。因此输出的结果必然也有2x2=4种：

```java
Tony's age is 10
Tony's age is 20
Jim's age is 10
Jim's age is 20
```

