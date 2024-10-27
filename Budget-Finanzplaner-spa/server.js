const express = require('express');
const app = express();
app.use(express.static('./dist/Budget-Finanzplaner-spa'));
app.get('/*', (req, res) =>
 res.sendFile('index.html', {root: 'dist/Budget-Finanzplaner-spa/'}),
);
app.listen(process.env.PORT || 8080);
