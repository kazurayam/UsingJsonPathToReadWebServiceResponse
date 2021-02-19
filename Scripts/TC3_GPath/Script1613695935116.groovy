import groovy.json.JsonSlurper

def text = '''{
	"results" : [
	   {
		  "address_components" : [
			 {
				"long_name" : "Jefferson Ave",
				"short_name" : "Jefferson Ave",
				"types" : [ "route" ]
			 },
			 {
				"long_name" : "North Newport News",
				"short_name" : "North Newport News",
				"types" : [ "neighborhood", "political" ]
			 },
			 {
				"long_name" : "Newport News",
				"short_name" : "Newport News",
				"types" : [ "locality", "political" ]
			 },
			 {
				"long_name" : "Virginia",
				"short_name" : "VA",
				"types" : [ "administrative_area_level_1", "political" ]
			 },
			 {
				"long_name" : "United States",
				"short_name" : "US",
				"types" : [ "country", "political" ]
			 },
			 {
				"long_name" : "23608",
				"short_name" : "23608",
				"types" : [ "postal_code" ]
			 }
		  ],
		  "formatted_address" : "Jefferson Ave & Denbigh Blvd, Newport News, VA 23608, USA",
		  "geometry" : {
			 "location" : {
				"lat" : 37.13852930,
				"lng" : -76.52013079999999
			 },
			 "location_type" : "APPROXIMATE",
			 "viewport" : {
				"northeast" : {
				   "lat" : 37.13987828029151,
				   "lng" : -76.51878181970848
				},
				"southwest" : {
				   "lat" : 37.13718031970851,
				   "lng" : -76.52147978029149
				}
			 }
		  },
		  "types" : [ "intersection" ]
	   }
	],
	"status" : "OK"
 }
'''

def json = new JsonSlurper().parseText(text)

def theNode = json.results[0].address_components.find { it.types[0] == 'postal_code' }

println "theNode=${theNode}"

assert '23608' == theNode.long_name