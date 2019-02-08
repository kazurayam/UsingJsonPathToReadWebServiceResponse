import java.nio.file.Path
import java.nio.file.Paths

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.kms.katalon.core.configuration.RunConfiguration

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/**
 * TC1 --- a demonstration how to use GPath in order to parse
 * a JSON as Web Service Response.
 *
 * GPath is explained at 
 * http://docs.groovy-lang.org/latest/html/documentation/core-semantics.html#gpath_expressions
 * 
 * The target JSON is shown in the following post in the Katalon Forum.
 * https://forum.katalon.com/t/get-json-value-with-skipping-1st-and-2nd-level-of-dynamic-value/18972/5
 */



/**
 * format a Object into a JSON string
 */
def prettyPrint(obj) {
	return JsonOutput.prettyPrint(JsonOutput.toJson(obj))
}

// json file as input
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path responseFile = projectDir.resolve('Include/resources/fixture/response.json')

// read the file
def json = responseFile.toFile().getText('UTF-8')

// parse the text, turn it into a JSON document
JsonSlurper jsonSlurper = new JsonSlurper()
Object document = jsonSlurper.parseText(json);

// just to see the document conent
//WebUI.comment(json)

// Case 0 - see the root
def fragment0 = document
println "\n---------- Case 0 ----------\n" + prettyPrint(fragment0)

// Case 1
def fragment1 = document.'-1'[0]
println "\n---------- Case 1 ----------\n" + prettyPrint(fragment1)
/* here you will see
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

// Case 2
def fragment2 = document.'-1'[0].collect({ it })
println "\n---------- Case 2 ----------\n" + prettyPrint(fragment2)
/* here you will see
[
    {
        "value": {
            "build": "",
            "createdDate": "",
            "description": "",
            "endDate": "",
            "ended": "",
            "environment": "",
            "executionSummaries": {
                "executionSummary": [
                    
                ]
            },
            "expand": "executionSummaries",
            "isExecutionWorkflowEnabledForProject": true,
            "isTimeTrackingEnabled": true,
            "modifiedBy": "",
            "name": "Ad hoc",
            "projectId": 14900,
            "projectKey": "TUT",
            "startDate": "",
            "started": "",
            "totalCycleExecutions": 2,
            "totalExecuted": 0,
            "totalExecutions": 0,
            "versionId": -1,
            "versionName": "Unscheduled"
        },
        "key": "-1"
    },
    {
        "value": {
            "build": "",
            "createdBy": "sysadmin",
            "createdByDisplay": "System Administrator",
            "createdDate": "2019-01-30 17:41:12.403",
            "description": "Create cycle from Katalon",
            "endDate": "",
            "ended": "",
            "environment": "",
            "executionSummaries": {
                "executionSummary": [
                    
                ]
            },
            "expand": "executionSummaries",
            "isExecutionWorkflowEnabledForProject": true,
            "isTimeTrackingEnabled": true,
            "modifiedBy": "sysadmin",
            "name": "ST Cycle 1",
            "projectId": 14900,
            "projectKey": "TUT",
            "sprintId": 1,
            "startDate": "",
            "started": "",
            "totalCycleExecutions": 1,
            "totalDefects": 0,
            "totalExecuted": 0,
            "totalExecutions": 0,
            "totalFolders": 0,
            "versionId": -1,
            "versionName": "Unscheduled"
        },
        "key": "13"
    },
    {
        "value": 2,
        "key": "recordsCount"
    }
]

 */

// Case 3
def fragment3 = document.'-1'[0].
						findAll({ it.value instanceof Map }).
						collect({ it.value.name })
println "\n---------- Case 3 ----------\n" + prettyPrint(fragment3)
/* here you will see
[
	"Ad hoc",
	"ST Cycle 1"
]
 */


// Case 4
def fragment4 = document.'-1'[0].
					findAll({ it.value instanceof Map && it.value.name == 'ST Cycle 1' }).
					collect({ it.value }).createdDate
println "\n---------- Case 4 ----------\n" + prettyPrint(fragment4)
/* here you will see
[
	"2019-01-30 17:41:12.403"
]
 */

// Case 5
def cycleId = document.'-1'[0].
				findAll({ it.value instanceof Map && it.value.name == 'ST Cycle 1'}).
				collect({ it.key })[0]
println "\n---------- Case 4 ----------\n" +
			"Cycle Id of 'ST Cycle 1' is ${cycleId}\n"
			

