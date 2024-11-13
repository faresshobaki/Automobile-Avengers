const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

// MySQL Connection
const db = mysql.createConnection({
  host: "automotive-avengers.cdq0gekwqsuj.us-west-1.rds.amazonaws.com",
  user: "momoney",
  password: "Janbeast007",
  database: "automobileavengers"
});

db.connect(err => {
  if (err) {
    console.error('Could not connect to MySQL:', err);
    return;
  }
  console.log('Connected to MySQL database');
});

// Example API endpoint to get data
app.get('/api/data', (req, res) => {
  db.query('SELECT * FROM my_table', (err, results) => {
    if (err) {
      res.status(500).send('Error fetching data');
    } else {
      res.json(results);
    }
  });
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
