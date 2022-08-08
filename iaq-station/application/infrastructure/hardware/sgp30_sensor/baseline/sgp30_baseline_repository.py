import os
from typing import Tuple
from definitions import IAQ_REPO_DIR


class SGP30Repository:
    """
     A repository to save the SGP30 Baseline values (see the SGP30 datasheet for more information)

     This is just reading and writing the baseline values to a file stored inside the raspberry pi
    """

    # @todo: don't hardcode this value. Move it to appconfig and inject it during this class's instantiation
    BASELINE_FILE = os.path.join(IAQ_REPO_DIR, 'iaq_baseline.txt')

    BASELINE_SEPARATOR = ','

    def get_iaq_baseline(self) -> Tuple[int, int]:
        with open(SGP30Repository.BASELINE_FILE, 'a+') as file:
            file.seek(0)  # need this to always read from the beginning of the file
            lines = file.read().split(SGP30Repository.BASELINE_SEPARATOR)
            eco2_baseline, tvocs_baseline = list(map(int, lines))  # convert the list of str to a list of int
            self.eco2_baseline = eco2_baseline
            self.tvocs_baseline = tvocs_baseline

        return self.eco2_baseline, self.tvocs_baseline

    def save_iaq_baseline(self, eco2_baseline: int, tvocs_baseline: int):
        self.eco2_baseline = eco2_baseline
        self.tvocs_baseline = tvocs_baseline

        content = "{eco2_baseline},{tvocs_baseline}".format(eco2_baseline=eco2_baseline, tvocs_baseline=tvocs_baseline)

        try:
            with open(SGP30Repository.BASELINE_FILE, 'w+') as file:
                file.write(content)
        except FileNotFoundError as error:
            print('WARNING, something wrong happened while saving the IAQ_BASELINE, exception is = ', error)
