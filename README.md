# roten-potatoes

sample request for DataManager: `http://localhost:8080/inventory?category=milk&timestamp=1479249799770`
sample output:
`{
  "timestamp": "2016-11-15T22:43:19Z",
  "geoInventories": [
    {
      "lat": 80.0,
      "lng": 23.0022,
      "items": [
        {
          "name": "Potatoes",
          "quantity": 1.3,
          "unit": "kg"
        },
        {
          "name": "Potatoes",
          "quantity": 1.3,
          "unit": "kg"
        }
      ]
    }
  ]
}`
