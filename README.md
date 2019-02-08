Using JsonPath in Katalon Studio
========

08 Feb, 2019
by kazurayam

## What is this?

This is a small [Katalon Studio](https://www.katalon.com/) project for demonstration purpose. You can download the zip of this project at the [Releases](https://github.com/kazurayam/UsingJsonPathToReadWebServiceResponse/releases) page.

This project was developed using Katalon Studio version 5.10.1.

This project was developed to propose a solution to a discussion ["Get json value with skipping 1st and 2nd level of dynamic value"](https://forum.katalon.com/t/get-json-value-with-skipping-1st-and-2nd-level-of-dynamic-value/18972) in the Katalon Forum.

## Problem to solve

The questioner has a JSON text as follows:
```
{
    "-1": [
        {
            "-1": {
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
            "13": {
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
            "recordsCount": 2
        }
    ]
}
```

He wants to look up "13" out of this JSON. He wants to do it with the `"name": "ST Cycle 1"` portion as key for look up.


Evan, the questioner, tried to parse the JSON using JsonSlurper, tried to consume it using basic Groovy language constructs (Iterator, for loop, etc). He found it difficult.

## Solution

Previously, [Vinh_Nguyen suggested](https://forum.katalon.com/t/capture-response-id-from-put-rest-api/16069/8) [JsonPath](https://github.com/json-path/JsonPath
) to analyze and selectively extract data out of JSON document. I think [JsonPath](https://github.com/json-path/JsonPath
) would be a powerful tool for anybody who wants to process Web Service Responses in JSON. However nobody in Katalon Forum posted sample which shows how to use JsonPath.

Then, I will do it.

As soon as I posted [the issue](https://forum.katalon.com/t/using-jsonpath-to-read-web-service-response/19040), Russ_Thomas responded and [suggested](https://forum.katalon.com/t/using-jsonpath-to-read-web-service-response/19040/2) that it is possible to extract data from JSON-origined object using only Groovy-native Collections API. I took his point and added a sample code.


## Description

### How to run the demos

1. download the zip of demo from [Releases](https://github.com/kazurayam/UsingJsonPathToReadWebServiceResponse/releases) page
2. unzip it and open it with your Katalon Studio
3. select `Test Cases/TC1_JsonPath` and run it. In the Console tab, you will find some output.
4. alternatively select `Test Cases/TC2_Collections` and run it. You will have similar output in the Console tab.

### How the demo is constructed

You can find and read the source code of testcases at:
- [`TC1_JsonPath`](Scripts/TC1_JsonPath/Script1549596754914.groovy)
- [`TC2_Collections`](Scripts/TC2_Collections/Script1549596782186.groovy)

The `Drivers/` directory contains the jar files of JsonPath downloaded from [Releases](https://github.com/json-path/JsonPath/releases) page.

The input file is located at [`Include/resources/fixture/response.json`](Include/resources/fixture/response.json)

### References

- [Jayway JsonPath](https://github.com/json-path/JsonPath) document for detail.
- http://mrhaki.blogspot.com/2009/10/groovy-goodness-finding-data-in.html for sample code of finding data in Collections in Groovy.
- http://mrhaki.blogspot.com/2010/05/groovy-goodness-use-collect-with.html for collect() method of Collections in Groovy

## Examples and outcomes

Here I show the code snippet and corresponding outputs.

### Case0 : show the JSON as is
Case0 code in TC1:
```
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path responseFile = projectDir.resolve('Include/resources/fixture/response.json')
def json = responseFile.toFile().getText('UTF-8')
Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

def fragment0 = JsonPath.read(document, '$')
println "\n---------- Case 0 ----------\n" + prettyPrint(fragment0)

```

Case0 code in TC2:
```
def json = responseFile.toFile().getText('UTF-8')
JsonSlurper jsonSlurper = new JsonSlurper()
Object document = jsonSlurper.parseText(json);

def fragment0 = document
println "\n---------- Case 0 ----------\n" + prettyPrint(fragment0)

```

output from TC1:
```
{
    "-1": [
        {
            "-1": {
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
            "13": {
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
            "recordsCount": 2
        }
    ]
}
```

The output from TC2 is just the same as TC1.


### Case1 : skipping the upper levels
Case1 code in TC1:
```
def fragment1 = JsonPath.read(document, '$.*.*')
println "\n---------- Case 1 ----------\n" + prettyPrint(fragment1)
```

Case2 code in TC2:
```
def fragment1 = document.'-1'[0]
println "\n---------- Case 1 ----------\n" + prettyPrint(fragment1)
```

output from TC1:
```
---------- Case 1 ----------
[
    {
        "-1": {
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
        "13": {
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
        "recordsCount": 2
    }
]
```

The output from TC2 is just the same as TC1.

### Case2 : skipping more
Case2 code in TC1:
```
def fragment2 = JsonPath.read(document, '$.*.*.*')
println "\n---------- Case 2 ----------\n" + prettyPrint(fragment2)
```

Case2 code in TC2:
```
def fragment2 = document.'-1'[0].collect({ it })
println "\n---------- Case 2 ----------\n" + prettyPrint(fragment2)
```

output from TC1:
```
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
```

To my surprise, **The output from TC2 is different from TC1**, as follows:
```
---------- Case 2 ----------
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
```

### Case3 : getting the values of `name`
Case3 code in TC1:
```
def fragment3 = JsonPath.read(document, '$.*.*.*.name')
println "\n---------- Case 3 ----------\n" + prettyPrint(fragment3)
```

Case3 code in TC2:
```
def fragment3 = document.'-1'[0].
						findAll({ it.value instanceof Map }).
						collect({ it.value.name })
println "\n---------- Case 3 ----------\n" + prettyPrint(fragment3)

```


output from TC1:
```
[
    "Ad hoc",
    "ST Cycle 1"
]
```

The output from TC2 is just the same as TC1.

### Case4 : filtering nodes by the value of `name`
Case4 code in TC1:
```
def fragment4 = JsonPath.read(document, '$.*.*.*[?(@.name==\'ST Cycle 1\')].createdDate')
println "\n---------- Case 4 ----------\n" + prettyPrint(fragment4)
```

Case4 code in TC2:
```
def fragment4 = document.'-1'[0].
					findAll({ it.value instanceof Map && it.value.name == 'ST Cycle 1' }).
					collect({ it.value }).createdDate
println "\n---------- Case 4 ----------\n" + prettyPrint(fragment4)

```

output from TC1:
```
---------- Case 4 ----------
[
    "2019-01-30 17:41:12.403"
]
```

The output from TC2 is just the same as TC1.

### Case 5 : extracting key value requires tricks
Case 5 code in TC1:
```
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
String cycleId = lookupCycleId(document, "ST Cycle 1")
println "\n---------- Case 4 ----------\n" +
			"Cycle Id of 'ST Cycle 1' is ${cycleId}\n"
```

Case 5 code in TC2:
```
def cycleId = document.'-1'[0].
				findAll({ it.value instanceof Map && it.value.name == 'ST Cycle 1'}).
				collect({ it.key })[0]
```

>Case5 code in TC2 is much shorter than TC1. It's better. But it is puzzling enough.

output from TC1:
```
---------- Case 5 ----------
Cycle Id of 'ST Cycle 1' is 13
```

The output from TC2 is just the same as TC1.

## Notes

The Zephyr for JIRA's original format uses Cycle ID (e.g, "13") as the key of Json objects. Why do they do that? I can't believe this! This makes consumer programs a lot complicated.

Pity, Evan. I feel your pain. Poorly designed JSON format is the cause of your problems.

I would rather like it to be in the format as follows:

```
    [
        ...
        { "cycleId":"13",
            "name":"ST Cycle 1",
            ...},
        { "cycleId":"...",
            "name":"...",
            ...},
        ...
    ]
```
The information contained here is no less than the original. The proposed format is far easier to look up Cycle Id. You can look up "13" by a simple JsonPath `$.*[name=='ST Cycle 1'][0].cycleId`. You do not need the lengthy `def lookupCycleId()` function.
