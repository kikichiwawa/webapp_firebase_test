import { useEffect, useState } from "react";
import Image from "../types/image";
import GetAllImageResponse from "../types/getAllImageResponse";
import FirebaseEntity from "../types/firebaseEntity";

const useImages = () => {
    const [allImages, setAllImages] = useState<Image[]>([]);
    const [imagesError, setErrorMsg] = useState<string | null>(null);
    
    const fetchImages = async()=>{
        try{
            const response = await fetch(`http://localhost:8080/fb/allImages`);
            if(!response.ok){
                throw new Error("Failed to fetch images");
            }
            const data: GetAllImageResponse = await response.json();
            const allImageData: Image[] = data.allImageEntity.map((fbEntity: FirebaseEntity<Image>)=>({
                ...fbEntity.entity,
                timestamp: fbEntity.entity.timestamp
            }));
            // const sortData = [...result.allImages].sort((a,b)=>a.timestamp-b.timestamp);
            setAllImages(allImageData);
        }catch(error){
            const errorMessage = error instanceof Error ? error.message : String(error);
            setErrorMsg(`Error: ${errorMessage}`);
        }
    }

    useEffect(() => {
        fetchImages();
    },[]);

    return {allImages, imagesError, fetchImages};
}

export default useImages;