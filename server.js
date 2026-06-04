import express from 'express';


// use sharp for loerm fetching we will recreate sharp we will have images in assests when ever somewhee request it will give random when we parameter it will in that size 

const app = express();
const port = 3000;
const images = [
    {
        width : 100,
        height : 100,
        path : "Propertymap.jpeg",
    },{
        width : 200,
        height : 200,
        path : "Propertymap.jpeg",
    }]

app.get("/images/:width/:height",(req,res) => {
  
    const {width,height} = req.query;
    const filteredImages = images.filter(image => image.width === width && image.height === height);
    if(filteredImages.length > 0){
        const image = filteredImages[Math.floor(Math.random() * filteredImages.length)];
        res.sendFile(image.path);
    }else{
        res.status(404).send("Image not found");
    }
})
app.listen(port,()=>{
    console.log(`Server is running on port ${port}`);
})
