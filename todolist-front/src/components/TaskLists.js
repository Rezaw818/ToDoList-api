import "./taskLists.css"
import Task from "./Task";


const TaskLists = ({tasks}) => {
    return(
        <div className="list">
            {tasks.map((item) => {
                return <Task key = {item.id} task={item} />
            })}
        </div>

        )

}


export default TaskLists