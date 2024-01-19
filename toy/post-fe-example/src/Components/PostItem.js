import '../Css/Main.css'
const PostItem = ( {Post} ) => {
    const { post_id, user_id, content, regist_date, like } = Post

    return (
        <tr className="board-posted">
            <td className="post_id">{ post_id }</td>
            <td className="user_id">{ user_id }</td>
            <td className="content">{ content }</td>
            <td className="regist_date">{ regist_date } </td> 
            <td className="type">{ like }</td> 
        </tr>
    )
}

export default PostItem;