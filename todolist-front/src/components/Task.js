import "./taskLists.css"

const Task = ({task}) => {
    return(
    <div className="list-item">{task.title}<input type="checkbox" value=""/></div>
    )

}


export default Task