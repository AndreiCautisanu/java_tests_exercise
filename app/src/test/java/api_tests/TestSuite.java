package api_tests;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

public class TestSuite {
    public static void main(String[] args) {
        TestNG testng = new TestNG();

        List<XmlSuite> suites = new ArrayList<>();

        XmlSuite suite = new XmlSuite();
        suite.setName("APITestSuite");

        XmlTest test = new XmlTest(suite);
        test.setName("APITest");

        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass(TestAPI.class));
        test.setXmlClasses(classes);

        suite.addTest(test);
        suites.add(suite);
        testng.setXmlSuites(suites);
        testng.run();
    }
}
