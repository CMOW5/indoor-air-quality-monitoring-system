import psutil
import os

"""
A script that checks if the given python program is running. if the program is not running, it will
start the program or do nothing otherwise.
This is mean to be used with crontab so the raspberry is always executing this program

# set crontab at every minute
crontab -e
* * * * * python /home/pi/iaq-station/monitor/run_iaq_monitor.py
"""


def check_if_process_is_running(name: str):
    """
    Check if there is any running process that contains the given name processName.
    """

    for proc in psutil.process_iter():
        try:
            # Check if process command line contains the given name string.
            if name.lower() in proc.cmdline():
                return True

        except (psutil.NoSuchProcess, psutil.AccessDenied, psutil.ZombieProcess):
            pass

    return False


def execute_process(name: str, args: str):
    """
    Executes the python process using the virtual environment (venv)
    """
    current_dir = os.path.dirname(os.path.abspath(__file__))
    root_dir = os.path.abspath(os.path.join(current_dir, os.pardir))

    concat_command = " && "
    cd_command = "cd " + root_dir
    activate_venv_command = ". bin/activate"  # for some reason 'source' doesn't work but . does
    # run the python program in the background
    execute_python_command = "python3 {name} {args} &".format(name=name, args=args)
    full_command = cd_command + concat_command + activate_venv_command + concat_command + execute_python_command

    os.system(full_command)


program_name = 'main_iaq_station.py'
program_args = 'aws-iot-core raspberry-pi raspberry-pi-configs'
# program_args = 'local-mqtt generic-linux generic-local-pc-configs'

if not check_if_process_is_running(program_name):
    print('starting process ....')
    execute_process(program_name, program_args)
else:
    print('process is already running ...')
