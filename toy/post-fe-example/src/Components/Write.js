import '../Css/Write.css'
import { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
const Write = () => {
    // const history = useHistory();
    const [ sendData, setSendData ] = useState({
        content: '',
        user_id: 'ssafy',
    })

    const { content, user_id } = sendData;

    const onChangeContent = e => {
        const nextSendData = {
            ...sendData,
            [ e.target.name ] : e.target.value,
        }
        setSendData( nextSendData );
    }

    const onClickSubmit = async () => {
        try{
            const response = await axios.post(
                'http://localhost:8080/posts', sendData
            );
            if( response.status === 200 ){
                alert("등록에 성공하였습니다.");
                window.location.href="/";
            }else{
                throw "등록 실패";
            }
        }catch( e ){
            console.log( e );
            alert("등록에 실패하였습니다.");
            window.location.reload();
            
        }
    }

    return(
        <div>
            <div className="wrapper">   
{/* 
                <div className="section title">
                    <input type="text" placeholder="제목"/>
                </div> */}
                <div className="section content">
                    <textarea name="content" placeholder="내용" onChange={ onChangeContent } value = { content }></textarea>
                </div>

                <div className="section footer">
                    <Link to='/'><a className="btn cancel" id="cancel">취소</a></Link>
                    <a className="btn save dark" id="submit" onClick={onClickSubmit}>출제</a>
                </div>
                </div>

        </div>
    )
}

export default Write;