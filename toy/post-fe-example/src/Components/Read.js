import '../Css/Read.css'
import { useState, useEffect } from 'react';
import { useLocation, Link } from 'react-router-dom';
import axios from 'axios';

const Read = () => {
	const[ post, setPost ] = useState({})
    const[ loading, setLoading ] = useState(false);
	const location = useLocation();
	const pathName = location.pathname;
	const lastPath = pathName.split('/').filter(Boolean).pop();
	const url = 'http://localhost:8080/posts/' + lastPath;

	const onChangePost = e => {
		const nextPost = {
			...post,
			[ e.target.name ] : e.target.value
		}
		setPost( nextPost );
	}

	useEffect( () => {
		const fetchData = async () => {
            setLoading(true);
            try{
                const response = await axios.get( url, );

            setPost( response.data.post );
            }catch( e ){
                console.log( e );
            }
            setLoading( false );
        };
        fetchData();
	}, [url])


	const onClickPut = async () => {
		try{
            const response = await axios.put(
                'http://localhost:8080/posts', post
            );
            if( response.status === 200 ){
                alert("수정에 성공하였습니다.");
                window.location.href="/";
            }else{
                throw "수정 실패";
            }
        }catch( e ){
            console.log( e );
            alert("수정에 실패하였습니다.");
            window.location.reload();
            
        }
	}

    return(
        <div>
            <div className="wrapper">
			<div className="section title">
				<div id="title_row">
					<input name="user_id" className="user_id" value={ post.user_id } onChange={ onChangePost }/>
					<input name="regist_date" className="regist_date" value={ post.regist_date } onChange={ onChangePost }/>
				</div>
				
				{/* <h2>
					제목
				</h2> */}

			</div>
			<div className="section content">
				<input className="quiz" name="content" value={ post.content } onChange={ onChangePost } />
			</div>
			<div className="section">
				{ 
					post.comments && post.comments.map( comment => {
					return <div className="comment"></div>
				})}
			</div>
			<div className="footer">
				<Link to ='/'><a className="btn modify" id="cancel">취소</a></Link>
				<a className="btn save dark" id="modify" onClick={ onClickPut}>수정</a>
			</div>
		</div>


        </div>
    )
}

export default Read;