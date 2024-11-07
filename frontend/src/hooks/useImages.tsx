import { useEffect, useState } from "react";
import Image from "../types/image";
import GetAllImageResponse from "../types/getAllImageResponse";

const useImages = () => {
    const [allImages, setAllImages] = useState<Image[]>([]);
    const [imagesError, setErrorMsg] = useState<string | null>(null);

    useEffect(() => {
        const fetchImages = async()=>{
            try{
                const response = await fetch(`http://localhost:8080/allImages`);
                if(!response.ok){
                    throw new Error("Failed to fetch images");
                }
                const data: GetAllImageResponse = await response.json();

                data.allImage = data.allImage.map((image: Image)=>({
                    ...image,
                    timestamp: new Date(image.timestamp)
                }));


                // const sortData = [...result.allImages].sort((a,b)=>a.timestamp-b.timestamp);
                console.log(data.allImage)
                setAllImages(data.allImage);
            }catch(error){
                const errorMessage = error instanceof Error ? error.message : String(error);
                setErrorMsg(`Error: ${errorMessage}`);
            }
        }

        fetchImages();
    },[]);

    return {allImages, imagesError};
}

export default useImages;