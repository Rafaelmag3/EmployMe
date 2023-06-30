const express = require('express');
const userRoutes = require('./routes/userRoutes');


const PORT = process.env.PORT || 3000;

const app = express();
app.use(express.json());
app.use(userRoutes);

//Hacer publica la carpeta
app.use('/uploads', express.static('uploads'));


app.get('/uploads/:nombreArchivo', (res, req) => {
  const nombreArchivo = req.params.nombreArchivo;
  res.sendFile(`${__dirname}/uploads/${nombreArchivo}`)
});



app.listen(PORT, () => {
  console.log(`Server listening on http://localhost:${PORT}`);
});
