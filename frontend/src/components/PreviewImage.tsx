import React, { useEffect, useState } from "react";
import Image from "../types/image";
import { getDownloadURL, ref } from "firebase/storage";
import { storage } from "../lib/firebase";
import { Timestamp } from "firebase/firestore";

const PreviewImage: React.FC<{image: Image}> = ({image}) => {
    const [prevUrl, setPrevUrl] = useState<string>("");
    const [error, setError] = useState<string>("");
    const [greyPrevUrl, setGreyPrevUrl] = useState<string| null>(null);



    useEffect(() => {    
        const getImageUrl = async () => {
            try{
                const imageRef = ref(storage, `${image.filePath}`);
                const url = await getDownloadURL(imageRef);
                setPrevUrl(url);
                console.log(url);
            }catch (e) {
                setError(`${e}`);
            }
        };

        const getGrayImageUrl = async () => {
            try{
                const imageRef = ref(storage, `${image.greyFilePath}`);
                const url = await getDownloadURL(imageRef);
                setGreyPrevUrl(url);
            }catch(e) {
                setGreyPrevUrl(null);
            }
        }
        
        if(image.filePath){
            getImageUrl();
        }
        
        getGrayImageUrl();

    }, [image.filePath, image.greyFilePath]);

    const handleDownload = async(url: string, fileName: string) => {
        // lobalhost:3000で構築している限り，ドメインが違うため別ウィンドウで開かれる可能性があり
        // デプロイ後も上手くいかない場合，次の記事を参照にする https://qiita.com/mooriii/items/39bb4c1fe948bf6207bf
        alert("ローカルで実装しているため，ブラウザで表示される可能性が高いです．");
        const a = document.createElement("a");
        a.href = url;
        a.download = fileName;
        a.click();
    }

    const handleDelete = async() => {
        alert(`${image.text}を削除しようとします(未実装)`);
    }

    const handleConvert = async() => {
        alert(`${image.text}白黒変換をしようとします(未実装)`);
    }

    const formatTimestamp = (timestamp: Timestamp) => {
        try{
            return timestamp.toDate().toLocaleString();
        }catch(e: any){
            alert(e);
            return "Invalid data";
        }
    }

    return(
        <div
            style={{
                boxShadow: "0px 4px 8px rgba(0, 0, 0, 0.2)",
                padding: 10,
                margin: 10,
                width: 350,
                height: 500,
                borderRadius: 8,
                backgroundColor: "#fff",
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "space-between",
            }}
        >
            {prevUrl ? (
                <img 
                    src={prevUrl} 
                    alt={image.text || "Preview"}
                    style={{
                        height:200,
                        width:300,
                        objectFit: "cover",
                        borderRadius: 8,
                    }} 
                />
            ): (
                <div
                    style={{
                        height:200,
                        width:300,
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        backgroundColor: "#f0f0f0",
                        borderRadius: 8,
                    }}
                >
                    <span>{error || "Loading..."}</span>
                </div>
            )}
            <div style={{ textAlign: "center"}}>
                <p style={{margin:0, fontWeight: "bold"}}>{image.text}</p>
                <p style={{margin:0, fontSize: "0.9rem", color: "#555"}}>
                    {formatTimestamp(image.timestamp)}
                </p>
            </div>
            <div style={{display: "flex", flexDirection: "column", gap:"8px", marginTop:"10px"}}>
                <button
                    onClick={() => handleDownload(prevUrl, `${image.text}.jpg`)}
                    style={buttonStyle}
                >
                    Download Image
                </button>

                {greyPrevUrl ? (
                    <button
                        onClick={() => handleDownload(greyPrevUrl, "grey_image.jpg")}
                        style={buttonStyle}
                    >
                        Download Grey Image
                    </button>
                ): (
                    <button
                    onClick={() => handleConvert()}
                    style={convertButtonStyle}
                >
                    Convert to Grey Image
                </button>
                )}

                <button onClick={handleDelete} style={deleteButtonStyle}>
                    Delete Image
                </button>
            </div>
        </div>
    );
}


const buttonStyle: React.CSSProperties = {
    padding: "10px 15px",
    backgroundColor: "#007BFF",
    color: "#fff",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "1rem",
    transition: "all 0.3s ease",
};

const convertButtonStyle: React.CSSProperties = {
    padding: "10px 15px",
    backgroundColor: "#28a745",
    color: "#fff",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "1rem",
    transition: "all 0.3s ease",
};

const deleteButtonStyle: React.CSSProperties = {
    padding: "10px 15px",
    backgroundColor: "#dc3545",
    color: "#fff",
    border: "none",
    borderRadius: "5px",
    cursor: "pointer",
    fontSize: "1rem",
    transition: "all 0.3s ease",
};

export default PreviewImage;