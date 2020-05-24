package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class HeapAlgorithm{
    public List<List<String>> permute(ArrayList<String> arr) {
        List<List<String>> list = new ArrayList<>();
        Arrays.sort(new ArrayList[]{arr});
        permuteHelper(list, new ArrayList<>(), arr,new boolean[arr.size()]);
        return list;
    }


    private void permuteHelper(List<List<String>> list, List<String> resultList, ArrayList<String> arr, boolean [] used){

        // Base case
        if(resultList.size() == arr.size()){
            list.add(new ArrayList<String>(resultList));
        } else{
            for(int i = 0; i < arr.size(); i++){
                if(used[i] || i > 0 && arr.get(i).equals(arr.get(i - 1)) && !used[i - 1])
                {
                    // If element is already used
                    continue;
                }
                // choose element
                used[i] = true;
                resultList.add(arr.get(i));

                // Explore
                permuteHelper(list, resultList, arr, used);

                // Unchoose element
                used[i] = false;
                resultList.remove(resultList.size() - 1);
            }
        }
    }
}
