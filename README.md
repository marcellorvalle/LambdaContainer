# LambdaContainer
Yet another DIC(Powered with lambda expressions).

This is a personal project and my objective is to learn a bit more about Java + JUnit + Gradle. 

There are some nice solutions like Spring, Google Guice and Dagger but sometimes they seem overcomplicated for my needs. I also dislikes the excessive use of @Inject (from JSR-330) annotation at the target Objects. It's a personal opinion but I think the least one class know about how it is going to be used, the better. Also, I have a lot of FUN with DIY projects.

I decided to use Lambda Expressions motivated by [Fabien Potencier's work](http://fabien.potencier.org/) on [Twittee](http://twittee.org/) and its bigger brother [Pimple](http://pimple.sensiolabs.org/), both using PHP.

##Basic functionality

I ~~copied~~ got inspired by Google Guice fluid interface and decided to implement something like that. The two main classes a developer will interact are ContainerFacade and Scenario. The first one contains an entry point to the servicer provided by this API and the second should be extended to configure the container.

```java

ContainerFacade cf = new ContainerService();
cf.setScenario(new MyScenarioImplementation());
FooInterface foo = cf.resolve(FooInterface.class);
```

###Scenario implementation example

```java
import com.mrv.lambdacontainer.Scenario;

public class MyScenarioImplementation  extends Scenario{
    @Override
    protected void setResolutions() {
        //clear all resolutions;
        clear();
        
        //Assign FooImplementation as a solution for FooInterface
        resolve(FooInterface.class).with(
            () -> new FooImplementation() 
        );
        // Obs: Different instances
        // assertNotSame(facade.resolve(FooInterface.class), facade.resolve(FooInterface.class));
        
        //Complex solution
        resolve(BarInterface.class).with(
            () -> {
                BarImplementation bar = new BarImplementation();
                bar.setParameterA(parameterA);
                //(...)do whatever is needed to configure it(...)
                return bar;
            }
        );
        
        //Singleton solution
        resolve(QuxInterface.class).withSingleton(
            () -> new QuxImplementation()
        );
        // Obs: Same instance
        // assertSame(facade.resolve(QuxInterface.class), facade.resolve(QuxInterface.class));
        
        
        //Extending existing solutions
        resolve(FooInterface).addExtension(
            (original) -> {
                original.setParameterA(parametarA);
                //(...)
                return original;
            }
        );
        
        //Override existing solutions
        resolve(BarInterface).override(
            () -> new BarImplementation()
        );
    }
}

```

## What to do next...

* Support @Inject annotation
* ~~Implement an interface a little bit more fluid~~
* ~~Support for different configuration scenarios~~
