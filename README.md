# break_client
Order details and qr code scanner for break app (check repo)

   author: darshan099
   Online Database: google spreadsheet
   This app can only be used with Break app (check repo)
   Dependecies used:
   1. 'com.squareup.okhttp:okhttp:2.4.0' : http client connection
   2. 'com.squareup.okhttp:okhttp-urlconnection:2.2.0'
   3. 'com.journeyapps:zxing-android-embedded:3.4.0' : scanning qr codes
   4. 'com.google.zxing:core:3.2.1'
   Permission needed:
   1) camera : to get the qr code
   2) stable internet connection
   Program index:
   1) reading quantity from google spreadsheet: line=127
   2) update the order: line=206
   3) scanning qr code: line=77,86
   Overview:
   this app will enable you to decode the qr code from break app
   working: (this app will only work with the break app, check repo)
   1) decode the qr code
   2) list all the orders and update the list
   3) if the qr code is used twice, show invalid
   possible issues/bugs:
   nothing yet
