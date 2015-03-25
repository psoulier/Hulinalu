#Overview
There are many web-based tools and applications for navigating, locating places,
finding reviews, weather conditions, and so forth. Few services, however,
provide current, real-time information relevant to a specific physical
locations. This is not overly surprising considering the difficulty in
collecting and interpreting this type of data. Many locations are remote or
exist where electronic equipment is not viable due to cost, environment etc.
Even when web cams are available, they offer only a limited overall view of the
surrounding environment. Navigation tools can provide in-route traffic
conditions, but do not give information about the destination. 

Consider, for example, an individual planning a trip to the beach. Various
factors are important depending on what activities are planned. Some information
is readily available. Surf and weather conditions are easily obtained and some
beaches may even have web cams. However, obtaining additional information such
as crowd size, parking availability, and water clarity for divers/snorkelers is
not as readily available. 

The best source of information may be people currently at a location with an
Internet-connected mobile device. An application that would allow people to
share information about a location who are currently there with others who are
interested in that location would help address this problem. In essence, the
system will dynamically present current conditions for a location by utilizing
the information provided by people at that location.

#Approach
This web application will provide two basic components: the producer and the
consumer. The producer component allows users of the application to contribute
real-time data relevant to a specific location. This may considered a form of
crowdsourcing. The consumer aspect allows users to check the current conditions
(provided by the producers) of a specific location. The concept is that
consumers become producers if they decide to go to a location. The hope is that,
for reasonably popular locations, a steady feed of up-to-date
information is produced.

There are several issues that arise from such a system:

1. Users that only consume data and do not participate in the information
  production.
2. Users that generate false data - specifically when not physically present at a
  location.
3. A location without sufficient updates to provide accurate data. This may exist
  as variable sized gaps in time.

To minimize subjectivity and to allow for statistical analysis, a set of locals
will be provided (e.g.: beach, trail, etc.) with predefined information
categories. Each category will have a predefined set of selections that can
easily be assigned a numerical value for subsequent processing.

## Producer
The Producer component will allow a user to provide current information for a
location they are physically at. Location services provided by a mobile device
(i.e.: GPS) will allow the web app to determine if a user is at a location
within a given tolerance. When a user runs the application, they will be
provided an update screen with predefined information fields if they are at a known location. 
By utilizing physical location, this prevents users from SPAMing the system or arbitrarily updating
conditions for a location at which they are not present. This addresses issues 2
above.

To facilitate the addition of new locations, a user may request the addition of
based on current location. When a sufficient number of users add the same
location (as determined by location services of the device), the location will
become available to other users for future use.

## Consumer
The consumer UI will allow users to view various locations and the associated
conditions of that location. In terms of usability, this aspect of the
application would be similar to many other services providing location-based
information such as customer reviews, map location, etc. A map will be provided
with markers where information is available. By selecting a marker, additional
information will be provided about that location.

## Credit System
A "credit" system will be used to avoid the first issue from above. Users will
use credits to access real-time data for a specific location and will receive
credit by providing information when physically present at a location. The
general intent of this system is to encourage a "give and take" mentality and
would generally not pose a noticeable hindrance to most users. When a user
creates an account, they will be given a sufficient number of credits to utilize
the system without the need to immediately "produce" information.

This system may not work - especially if it becomes overly intrusive to the user
experience. Care must be taken to ensure this system is more of a motivational
incentive. Another option may be to use badges or another "rewards" system that
is not compulsory.

## Data Analysis
Data provided by users at a location will be stored with a time stamp. Basic
statistics will be applied to data within a certain time interval to determine
the common consensus for the various conditions of a location. 

# Deliverable 1
Deliverable 1 will consist of the following components:

1. Landing page
2. Sign Up
3. Home

## Landing Page
The landing page is simply the first page shown for users that are not logged
in. It will describe the service the website provides, a login for
existing users, and a link for new users to create an account. While simple in
function, this page should be ascetically pleasing and convey the general
concept of the service the website provides.

## Sign Up
The sign up page will allow a user to create a new account. This will be a
typical account creation form requiring the user to provide a certain amount of
information and optionally allowing the user to add additional information to
customize their profile. Required fields for sign up are the following:
- Valid e-mail address
- Account nickname to identify user online

Optional information will include:
- Full name
- General location (zip code, address, or similar)
- Mobile phone number 

## Home 
The Home page is the primary interface for the user and will contain the
system's core functionality. It will show the users current location and provide
the option to share information about that location if it's known to the system.
If the location is not known, it will provide a mechanism to suggest the
location to be added or to be ignored (if, for example, its their home, office,
etc.). A location that is "suggested" will only become publicly available once a
sufficient number of users suggest the same place.

This page will also provide an interface to view the conditions of various
locations based on a category (e.g.: trails, beaches, etc.). This interface will
allow the user to select predefined categories. Once selected, locations
available in the system within a certain radius will be shown on a map. The user
will be able to select markers on the map to show detailed information about
that location. Selecting a location will deduct credits from the user's account.
The information associated to 

# Deliverable 2
Deliverable 1 provides only a simple mechanism for finding locations of
interest. The primary goal of Deliverable 2 is to add more comprehensive search
features. These features would provide the ability to search for locations based
on query strings, locations (e.g.: addresses, zip codes, etc.), or locations
with specific current conditions (e.g.: available parking, not crowded, etc.). 

# Deliverable 3
The first two deliverables focused primarily on core functionality. The focus of
the third deliverable is to identify users that are "key contributors". Users
that have a history of routinely posting accurate updates can be considered more
reliable than other users. The input from such users should carry a heavier
weight to indicate the input they provide should be regarded as more accurate.  
By using "badges" or some form of reward system the system will encourage good
users. Additionally, due to a heavier weight associated to a users input,
inaccuracies from locations with fewer updates can be minimized.

# Mockup
