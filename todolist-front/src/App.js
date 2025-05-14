import logo from './logo.svg';
import './App.css';
import AddTask from "./components/AddTask";
import TaskLists from "./components/TaskLists";
import {useState} from "react";


function App() {
    const [tasks, setTasks] = useState(
        [
            {id : 1, title : 'study math'},
            {id : 2, title : 'study'}
        ]
    )

    const addTaskHandler = () => {

    }

  return (

      <div>
        <AddTask onClick = {addTaskHandler}/>

        <TaskLists tasks = {tasks}/>
      </div>


  );
}

export default App;
