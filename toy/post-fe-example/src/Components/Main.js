
import '../Css/Main.css'
import { Link } from 'react-router-dom';
import axios from 'axios';
import PostItem from './PostItem';
import { useState, useEffect } from "react";


const Main = () => {

    const[ posts, setPosts ] = useState([])
    const[ loading, setLoading ] = useState(false);
    const[ pageNum, setPageNum ] = useState( 0 );

    useEffect( () => {
        const fetchData = async () => {
            setLoading(true);
            try{
                const response = await axios.get( 'http://localhost:8080/posts', );

            setPosts( response.data.posts );
            }catch( e ){
                console.log( e );
            }
            setLoading( false );
            setPageNum( posts.length % 12 === 0 ? posts.length / 12 : posts.length / 12 + 1 );
        };
        fetchData();
    }, []);

    return(
        <div>
            <div className="contents">
                <div className="serchbar">
                    <input type="text"/>
                    <i className="fa-solid fa-magnifying-glass" id="searchicon"></i>
                </div>
                <div className="post-area">
                    <Link to="/write" className="btn dark write">글 작성하기</Link>
                </div>
                <div className="board">
                    <table>
                        <colgroup>
                            <col className="sizeNum"/>
                            <col className="sizeObject"/>
                            <col className="sizeTitle"/>
                        </colgroup>
                        <thead>
                            <tr className="board-title">
                                <th>번호</th>
                                <th>작성자</th>
                                <th>내용</th>
                                <th>작성일</th>
                                <th>좋아요</th>
                            </tr>
                        </thead>
                        <tbody>
                            { posts.map( (post, index ) => {
                                if( index > 11 ){
                                    return null;
                                }
                                <PostItem key={post.post_id} Post={post} />
                            })
                            }
                            {/* <tr className="board-posted">
                                <td className="num">---</td>
                                <td className="writer">---</td>
                                <td className="title">---</td>
                                <td className="date"></td> 
                                <td className="view"></td> 
                            </tr>
                            <tr className="board-posted">
                                <td className="num">---</td>
                                <td className="writer">---</td>
                                <td className="title">---</td>
                                <td className="date"></td> 
                                <td className="view"></td> 
                            </tr>
                            <tr className="board-posted">
                                <td className="num">---</td>
                                <td className="writer">---</td>
                                <td className="title">---</td>
                                <td className="date"></td> 
                                <td className="view"></td> 
                            </tr> */}
                        </tbody>
                    </table>
                </div>
                <div className="paging">
                    <ul className="pagination">
                        <li className=""><a>&laquo;</a></li>
                        {Array.from({ length: {pageNum} }, (_, index) => (
                            <li key={index + 1}>{ index + 1 }</li>
                        ))}
                        {/* <li className=""><a>1</a></li>
                        <li className=""><a>2</a></li>
                        <li className=""><a>3</a></li>
                        <li className=""><a>4</a></li>
                        <li className=""><a>5</a></li>
                        <li className=""><a>6</a></li>
                        <li className=""><a>7</a></li>
                        <li className=""><a>8</a></li>
                        <li className=""><a>9</a></li> */}
                        <li className=""><a>&raquo;</a></li>
                    </ul>
                </div>
            </div>
        </div>

    )
}

export default Main;







