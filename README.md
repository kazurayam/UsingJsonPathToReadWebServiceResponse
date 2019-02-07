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

Previously, [Vinh_Nguyen suggested](https://forum.katalon.com/t/capture-response-id-from-put-rest-api/16069/8) [JsonPath](https://github.com/json-path/JsonPath/releases
) to analyze and selectively extract data out of JSON document. I think [JsonPath](https://github.com/json-path/JsonPath/releases
) would be a powerful tool for anybody who wants to process Web Service Responses in JSON. However nobody in Katalon Forum posted sample which shows how to use JsonPath.

Then, I will do it.

## Description

### How to run the demo

1. Download the zip of demo from [Releases](https://github.com/kazurayam/UsingJsonPathToReadWebServiceResponse/releases) page
2. unzip it and open it with your Katalon Studio
3. select `Test Cases/TC1` and run it.
4. in the Console tab, you will find the output

### How the demo is constructed

You can find and read the source code of `TC1` located [here](Scripts/TC1/Script1549520402040.groovy).

The `Drivers/` directory contains the jar files of JsonPath downloaded from [Releases](https://github.com/json-path/JsonPath/releases) page.

The input file is located at [`Include/resources/fixture/response.json`](Include/resources/fixture/response.json)

### JsonPath examples and outcomes

Here I show the code snippet and corresponding outputs. I would not describe the meanings of JsonPath expressions here. Please read the original [Jayway JsonPath](https://github.com/json-path/JsonPath) document for detail.

#### Case0
code:
```
Path projectDir = Paths.get(RunConfiguration.getProjectDir())
Path responseFile = projectDir.resolve('Include/resources/fixture/response.json')
def json = responseFile.toFile().getText('UTF-8')
Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
def fragment0 = JsonPath.read(document, '$')
println "\n---------- Case 0 ----------\n" + prettyPrint(fragment0)

```

output:
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

#### Case1
code:
```
def fragment1 = JsonPath.read(document, '$.*.*')
println "\n---------- Case 1 ----------\n" + prettyPrint(fragment1)
```

output:
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

#### Case2
code:
```
def fragment2 = JsonPath.read(document, '$.*.*.*')
println "\n---------- Case 2 ----------\n" + prettyPrint(fragment2)
```

output:
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

#### Case3
code:
```
def fragment3 = JsonPath.read(document, '$.*.*.*.name')
println "\n---------- Case 3 ----------\n" + prettyPrint(fragment3)
```

output:
```
[
    "Ad hoc",
    "ST Cycle 1"
]
```

#### Case4
code:
```
def fragment4 = JsonPath.read(document, '$.*.*.*[?(@.name==\'ST Cycle 1\')].createdDate')
println "\n---------- Case 4 ----------\n" + prettyPrint(fragment4)
```

output:
```
---------- Case 4 ----------
[
    "2019-01-30 17:41:12.403"
]
```

#### Case 5
code:
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

output:
```
---------- Case 5 ----------
Cycle Id of 'ST Cycle 1' is 13
```

## Notes

The Zephyr for JIRA's original format uses Cycle ID (e.g, "13") as a key of Json object. Why do you do that?

 This makes consumer program a lot complicated. Pity, Evan.

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
The information contained here is no less than the original. The proposed format is far easier to look up Cycle Id value with key of `name` value. You can look up "13" by a simple JsonPath `$.*[name=='ST Cycle 1'][0].cycleId`. You do not need the lengthy `def lookupCycleId()` function.
