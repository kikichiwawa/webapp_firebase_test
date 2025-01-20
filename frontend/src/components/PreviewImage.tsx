import React, { useEffect, useState } from "react";
import Image from "../types/image";
import { getDownloadURL, ref, StorageReference } from "firebase/storage";
import { storage } from "../lib/firebase";

const PreviewImage: React.FC<{image: Image}> = ({image}) => {
    const [prevUrl, setPrevUrl] = useState<string>("");
    const [error, setError] = useState<string>("");

    useEffect(() => {
        const getImageUrl = async (imgageRef: StorageReference) => {
            try{
                const url = await getDownloadURL(imgageRef);
                setPrevUrl(url);
            }catch (e) {
                setError(`${e}`);
            }
        };
        
        const imageRef = ref(storage, `images/${image.filePath}`);
        getImageUrl(imageRef);
    }, [image.filePath]);


    return(
        <div
            style={{
                boxShadow: "0px 4px 8px 16px",
                padding: 10,
                margin: 10,
                width: 350,
                height: 400,
            }}
        >
            <p>
                <img 
                    src={prevUrl} 
                    alt={error} 
                    style={{height:200, width: 300, objectFit: "cover"}}
                />
            </p>
            <p>{image.text}</p>
            <p>{image.timestamp.toString()}</p>
        </div>
    );
}

export default PreviewImage;