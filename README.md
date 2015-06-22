sdmxgateway
========

Sdmx Gateway

Hi!
This is an SDMX gateway to all the sdmx providers supported by sdmx-sax...
it will transmit data in different formats according to the Accepts header provided by the client...
so far, only the repository/data section is implemented.. 
supported input providers
1.    ABS - Australian Bureau of Statistics
2.    IStat - Italian Statistics Bureau
3.    OECD - OECD Statistics
4.    IMF - International Monetary Fund
5.    ECB - European Central Bank
6.    UIS - Unesco Institute for Statistics
7.    INEGI - Mexican Statistics Organisation
8.    FAO - Food and Agriculture Organisation (problems here!)
9.    ILO - International Labour Organisation
10.   Knoema - www.knoema.com
11.   AfDB - www.opendataforafrica.org
12.   NBB - National Bank of Belgium
13.   UKDS - United Kingdom Data Service
    
     
supported output data formats;
    sdmx xml structure specific 2.1
    sdmx xml generic 2.1
    sdmx-json wd 1.0.0
    json-stat 1.0

Unfortunately you must know the SDMX Query String to use to get the data, and for this you need the structure to obtain the IDs...
so really the only useful formats are sdmx-json and json-stat...


queries can be formatted this way;
http://www.notionworks.com.au/sdmxgateway/rest/repository/1/data/LF/0.14.3.1599.30.M/ABS?startPeriod=2010-01-01&endPeriod=2015-01-01
                                                         {provider}/data/{flowRef}/{queryString}/{providerRef}?startPeriod={startPeriod}&endPeriod={endPeriod}
provider is the number next to the provider list above, here, '1' is the ABS.


for comments please email me <jsg at internode.on.net>

