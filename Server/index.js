const express = require('express');
const userRoutes = require('./routes/userRoutes');


const PORT = process.env.PORT || 3000;

const app = express();
app.use(express.json());
app.use(userRoutes);


app.listen(PORT, () => {
  console.log(`Server listening on http://localhost:${PORT}`);
});
