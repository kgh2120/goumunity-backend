import logo from './logo.svg';
import { Route, Routes } from 'react-router-dom';
// import './App.css';
import Main from './Components/Main';
import Read from './Components/Read';
import Write from './Components/Write';
// import './Css/Main.css'
const App = () => {
  return(
    <Routes>
      <Route path="/" element={<Main/>} />
      <Route path="/posts/:id" element={<Read/>} />
      <Route path="/write" element={<Write/>}/>
    </Routes>
  )
}

export default App;
