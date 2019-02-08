import java.nio.file.Path
import java.nio.file.Paths

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.kms.katalon.core.configuration.RunConfiguration

import groovy.json.JsonOutput

/**
 * TC1 --- a demonstration how to use Jayway JsonPath in order to parse
 * a JSON as Web Service Response.
 * 
 * Jayway JsonPath is available at https://github.com/json-path/JsonPath
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
Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

// just to see the document conent
//WebUI.comment(json)

// Case 0 - see the root 
def fragment0 = JsonPath.read(document, '$')
println "\n---------- Case 0 ----------\n" + prettyPrint(fragment0)

// Case 1
def fragment1 = JsonPath.read(document, '$.*.*')
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
def fragment2 = JsonPath.read(document, '$.*.*.*')
println "\n---------- Case 2 ----------\n" + prettyPrint(fragment2)
/* here you will see 
[
    {
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
    {
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
    2
]
 */

// Case 3
def fragment3 = JsonPath.read(document, '$.*.*.*.name')
println "\n---------- Case 3 ----------\n" + prettyPrint(fragment3)
/* here you will see
[
    "Ad hoc",
    "ST Cycle 1"
]
 */


// Case 4
def fragment4 = JsonPath.read(document, '$.*.*.*[?(@.name==\'ST Cycle 1\')].createdDate')
println "\n---------- Case 4 ----------\n" + prettyPrint(fragment4)
/* here you will see
[
	"2019-01-30 17:41:12.403"
]
 */


/**
 * a function to look up Cycle Id in the response from Zephyr
 * see https://forum.katalon.com/t/get-json-value-with-skipping-1st-and-2nd-level-of-dynamic-value/18972 for the definition of the problem
 * 
 * Here I want to lookup the key "13" out of {.., "13":{.., "name":"ST Cycle 1", ..}, ..}
 * 
 * What a terrible format of input JSON! I dislike it. It should rather be:
 *     [ ..., {"id":"13", "name":"ST Cycle 1", ...}, ... ]
 * 
 * @param doc
 * @param cycleName
 * @return
 */
def lookupCycleId(doc, cycleName) {
	List<Map> map2 = JsonPath.read(doc, '$.*.*')
	for (Map m : map2) {
		Set<String> keySet = m.keySet()
		for (String key : keySet) {
			Map map3 = m.get(key)
			if (map3.name == cycleName) {
				return key
			}	
		}
	}
	return null
}

// Case 5
String cycleId = lookupCycleId(document, "ST Cycle 1")
println "\n---------- Case 4 ----------\n" + 
			"Cycle Id of 'ST Cycle 1' is ${cycleId}\n"