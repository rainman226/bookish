import React, { useEffect, useState } from 'react';
import './Bookshelf.css';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import BookDetailsModal from './BookDetailsModal';
import WishSearch from "../WishList/WishSearch";

const Bookshelf = () => {

  const {id} = useParams();
  const [data,setData] = useState(null);
  const [selectedBook,setSelectedBook] = useState(null);
  const [newBookId, setNewBookId] = useState('');
    const [carte ,setCarte] = useState('');


  useEffect(() =>{

   const getdata = async () =>{

    try{
   const response = await axios.get(`http://localhost:8080/profiles/bookshelf/${id}`);
    console.log(response.data)
    setData(response.data);

      }catch(err)
{

}

   }

getdata();
  },[])
  
 const openModal = (book) => {
    setSelectedBook(book);
  };

  const closeModal = () => {
    setSelectedBook(null);
  };

  const handleCarte = (param) =>{
   setCarte(param)
   console.log(carte.id)
  }
  const deleteEntry = async (data) =>{
    try{
      const response = await axios.delete(`http://localhost:8080/profiles/bookshelf/delete?bookID=${data}&ownerID=${id}`)
      window.location.reload();
    }catch(err)
    {
      console.log(err);
    }
  }

  const addBook = async () => {
 try {
      const response = await axios.post('http://localhost:8080/profiles/bookshelf/add', {
        bookID: carte.id,
        userID: id,
      });

      // Assuming the response contains updated bookshelf data
      // setData(response.data);
      setNewBookId(''); // Clear the input field after successful addition
      window.location.reload();
    } catch (err) {
      console.error(err);
    }
  }



  // if(!data){
  //  return ( <div>Data loading cuh</div>)
  // }


  return (
    <div className='anunturi-bookshelf-container'>
     <div className='anunturi-form-bookshelf-Container'>
  <div>
   <WishSearch onSelectBook={handleCarte}/>
          <button  onClick={addBook}> add</button>
          </div>
<div className="bookshelf">
      {data && data.map((book) => (
        // <div key={book.id} className="book" onClick={() => openModal(book)}>
        //  <img className='bookshelf-img'  src={book.thumb} alt="" />
       
          
         
        // </div>
        <div key={book.id} className="book" >
          <div>
         <img className='bookshelf-img'  onClick={() => openModal(book)} src={book.thumb} alt="" />
         </div>
         <div>
         <button onClick={() => deleteEntry(book.id)}>Delete</button></div>
         </div>
      ))}
    </div>

    {selectedBook && (
          <BookDetailsModal book={selectedBook} onClose={closeModal} />
        )}


     </div>
    </div>
  )
}

export default Bookshelf