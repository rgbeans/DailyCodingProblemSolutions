class Dce548:
    @staticmethod
    def time_to_angle(time:str):

        hour2deg:int = 30
        
        min2deg:int = 6

        if time.__len__() != 5: raise ValueError("argument wrong size")

        times = time.split(":")

        if len(times) != 2: raise ValueError("argument must be split by a single ':'")

        if times[0].__len__() != 2: raise ValueError("hour and minutes must be 2 digits each")

        hours = int(times[0]) if times[0].isdecimal() else None

        if hours is None: raise ValueError("hour must be an integer between 00 and 23")

        if hours < 0 or hours > 23: raise ValueError("hour must be an integer between 00 and 23")
        if hours > 12: hours -= 12

        minutes = int(times[1]) if times[1].isdecimal() else None

        if minutes is None: raise ValueError("minutes must be an integer between 00 and 59")
        if minutes < 0 or minutes > 59: raise ValueError("minutes must be an integer between 00 and 59")

        theta_h:float = hours * hour2deg + minutes * hour2deg / 60
        theta_m:float = minutes * min2deg

        result:float = abs(theta_h - theta_m)

        return int(round(result))


