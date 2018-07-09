# Problems met and solution: 
1. Intellij use the Finley(Spring boot 2.0) defalut, So I need to change:
    ```$xslt
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <!--<version>2.0.3.RELEASE</version>-->
        <version>1.5.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    ```
    And also:
    ```$xslt
        <!--<spring-cloud.version>Finchley.RELEASE</spring-cloud.version>-->
        <spring-cloud.version>Dalston.RELEASE</spring-cloud.version>
    ```

    In according, check all the Jar dependencies, and make sure they are compatable to the above version
 1. others?


