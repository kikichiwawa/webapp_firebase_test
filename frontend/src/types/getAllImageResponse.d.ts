import FirebaseEntity from "./firebaseEntity";
import Image from "./image";

interface GetAllImageResponse{
    allImageEntity : FirebaseEntity<Image>[],
}

export default GetAllImageResponse;