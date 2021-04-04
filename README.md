# Covid-19-Live-Status
This **COVID-19** app of ours mainly centralizes on the idea of pooling all the data into one cohesive platform, to help you track real-time information, do the correct trend analysis and make comparisons among different states of India and other countries. This mobile application will also include some awareness contents/ streamers to make the users realize to be more cautious and avoid the propagation of the virus.<br>

<p>
<img src = "https://github.com/Roshannahak/Covid-19-Live-Status/blob/main/screenshots/intro_ss.jpg"/>
</p>

## Purpose of Project
Since the start of the COVID outbreak, a plethora of mobile data collection applications have been developed to help users to report their symptoms and track the disease. In countries like South Korea these ‘apps’ have gone even further and provide authorities with the ability to alert users when they’ve been in direct contact with a confirmed positive case.<br> 

Seeing the outspread of this Covid-19 virus, an app which can make the users aware and help them get the real time quick data was highly needed. Hence we came up with an idea of making our own Covid-19 app through which users can track the confirmed, active, recovered and deceased cases of various states of India and World. 
We are using state bulletins and official handles to update our numbers. The data is validated by a group of volunteers and published into a Google sheet and an API. API is available for all at [api.covid19india.org](https://www.covid19india.org/) .<br>

Because it affects all of us. Today it's someone else who is getting infected; tomorrow it could be us. We need to prevent the spread of this virus. We need to document the data so that people with knowledge can use this data to make informed decisions.<br>


## Overview
**Covid-19** app provides the real time quick data so that users can track real-time information.
The detailed dashboard contains a statistical representation of the data on a pie chart done with the help of animated pie view library showing the active, recovered and deceased cases. The dashboard also shows the user the samples tested which is updated frequently.
We are using state bulletins and official handles to update our numbers. The data is validated by a group of volunteers and published into a Google sheet and an API. API is available for all at [api.covid19india.org](https://www.covid19india.org/) .<br>

MoHFW updates the data at a scheduled time. However, we update them based on state press bulletins, official (CM, Health M) handles, **PBI**, **Press Trust of India**, **ANI reports**. These are generally more recent.<br>

Further user can view the brief state and country wise data which also has different country flags.<br>
For easy search we have also added active search bar and voice search option for more user friendly experience.<br>
Pie charts representation of the data helps users understand the large figures easily in no time.<br>
User can also refresh the feed with the swipe up feature.<br>

The notification feature in the app will also remind the user to be more aware by keeping a safe distance and regular use of alcohol based hand rub sanitizer or washing hands with soaps. This notification will be frequently sent to the user in an adequate time interval to stop the propagation of the virus.<br>

<table border="1">
		<tr>
			<th width = "10%">No.</th>
			<th width = "100%">Key Features</th>
		</tr>
		<tr>
			<td>1.</td>
			<td>Detailed dashboard screen</td>
		</tr>
		<tr>
			<td>2.</td>
			<td>Current state and country Wise data</td>
		</tr>
		<tr>
			<td>3.</td>
			<td>Active search bar to search states and countries</td>
		</tr>
		<tr>
			<td>4.</td>
			<td>Active voice search</td>
		</tr>
		<tr>
			<td>5.</td>
			<td>Pie chart representation of data</td>
		</tr>
		<tr>
			<td>6.</td>
			<td>Visible Country Wise flags</td>
		</tr>
		<tr>
			<td>7.</td>
			<td>Swipe to refresh</td>
		</tr>
		<tr>
			<td>8.</td>
			<td>Notifications to make user aware</td>
		</tr>
  <tr>
			<td>9.</td>
			<td>Day/Night Theme</td>
		</tr>
	</table>
  
## Technology Used
Following are the details of the software as well as the libraries used to build the app. 

#### Software requirements:-
  * Android Studio (4.0.1)
  * Android device or emulator
  * Technology: 
    * java
    * XML
    * JSON
    * firebase cloud messaging service

#### Libraries Used:-
  * Glide
  * Retrofit 2
  * GSON
  * Animated pie view
  * Circle image view

#### API:-
  India: [ https://api.covid19india.org/data.json](https://api.covid19india.org/data.json)<br>
  World wide: [https://corona.lmao.ninja/v2/countries](https://corona.lmao.ninja/v2/countries)<br>
  
## Screenshot
<table>
		<tr>
			<td align="left" width = "50%">Dashboards are used to display the most important and useful information in your app.
Here is a detailed dashboard containing the vital information like active, recovered, confirmed and deceased cases along with the samples tested which is updated frequently on a pie-chart representation for user friendly experience.
</td>
			<td align = "center"><img src = "https://github.com/Roshannahak/Covid-19-Live-Status/blob/main/screenshots/dashboard.jpg" width = "250" height = "450"/></td>
		</tr>
	<tr>
			<td align="center"><img src = "https://github.com/Roshannahak/Covid-19-Live-Status/blob/main/screenshots/1508.png" width = "250" height = "500"/></td>
			<td align="left">Notifications that will remind the user to take precautionary actions like cover the mouth and nose with a bent elbow or tissue when you cough or sneeze. Disposal of tissue and wash hands regularly with soap or use alcohol based hand sanitizer.</td>
		</tr>
	<tr>
			<td align="center"><img src = "https://github.com/Roshannahak/Covid-19-Live-Status/blob/main/screenshots/country_list.jpg" width = "250" height = "450"/></td>
			<td align="center"><img src = "https://github.com/Roshannahak/Covid-19-Live-Status/blob/main/screenshots/state_list.jpg" width = "250" height = "450"/></td>
		</tr>
	</table>

## Developed by:
Roshan Nahak<br> Follow By: [Linkedin](https://www.linkedin.com/in/roshan-nahak-a15833193/) | [Hackerrank](https://www.hackerrank.com/roshannahak)
