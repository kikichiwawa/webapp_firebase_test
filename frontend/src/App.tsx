import React from 'react';
import './App.css';
import useImages from './hooks/useImages';
import Post from './components/Post';
import PreviewImage from './components/PreviewImage';


const App: React.FC = () => {
  const {allImages, imagesError, fetchImages} = useImages();
  return (
    <div className="App">
      <Post fetchImages={fetchImages}/>
      <h3>投稿一覧</h3>
      <p style={{color: "red"}}>{imagesError && imagesError}</p>
      <div className='container'>
        <div className='row'>
          {allImages.map((image, index) => (
            <div className='col-4' key={index}>
              <PreviewImage image={image}/>
              <button
                onClick={fetchImages}
                style={{ marginTop: "10px", cursor: "pointer" }}
              >
                リロード
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default App;
