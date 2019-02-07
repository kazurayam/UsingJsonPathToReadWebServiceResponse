import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

import java.nio.file.Path
import java.nio.file.Paths

import java.nio.file.Files

import com.kms.katalon.core.configuration.RunConfiguration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.Configuration

import groovy.json.JsonOutput

def prettyPrint(obj) {
	return JsonOutput.prettyPrint(JsonOutput.toJson(obj))	
}

/**
 * Example of applying Jayway JsonPath to read Web Service Response
 * 
 * see https://github.com/json-path/JsonPath
 */
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path responseFile = projectDir.resolve('Include/resources/fixture/response.json')

// read the file
def json = responseFile.toFile().getText('UTF-8')
Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

// just to see
//WebUI.comment(json)


List<Map> map1 = JsonPath.read(document, '$.*.*')
println prettyPrint(map1)
/* here you will see following output
[
    {
        "-1": {
            ...
            "name": "Ad hoc",
            ...
        },
        "13": {
            ...
            "name": "ST Cycle 1",
            ...
        },
        "recordsCount": 2
    }
]
 */



def lookup(doc, valueOfName) {
	List<Map> map2 = JsonPath.read(doc, '$.*.*')
	println "map2.size() = ${map2.size()}"
	for (Map m : map2) {
		println prettyPrint(m)
		Set<String> keySet = m.keySet()
		for (String key : keySet) {
			println "key=$key"   // key= "-1", "13", "recordsCount"
			Map map3 = m.get(key)
			if (map3.name == valueOfName) {
				return key
			}	
		}
	}
	return null
}

// I want to lookup the key "13" output the object {.., "13":{.., "name":"ST Cycle 1", ..}, ..}
String wyw = lookup(document, "ST Cycle 1")
println "what you want is ${wyw}"