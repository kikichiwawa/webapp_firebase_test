import { useEffect, useState } from "react";
import Image from "../types/image";
import GetAllImageResponse from "../types/getAllImageResponse";
import FirebaseEntity from "../types/firebaseEntity";
import { Timestamp } from "firebase/firestore";

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
            const allImageData: Image[] = data.allImageEntity.map((fbEntity: FirebaseEntity<Image>)=>{
                const timestamp = typeof fbEntity.entity.timestamp === 'string'
                    ? Timestamp.fromDate(new Date(fbEntity.entity.timestamp)) // 文字列をTimestampに変換
                    : fbEntity.entity.timestamp; // すでにTimestamp型の場合そのまま使用

                return {
                    ...fbEntity.entity,
                    timestamp, // Timestamp型に変換したものをセット
                }
        });
            allImageData.forEach((image:Image) => {
                console.log(image.timestamp);
                console.log(typeof image.timestamp);
                console.log(image.timestamp instanceof Timestamp)
            })
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